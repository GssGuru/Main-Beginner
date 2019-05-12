package guru.gss.mainbeginner;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FragmentNewsFeed extends Fragment {

    private final String URL = "https://newsapi.org/";
    private final String API_KEY = "7c4feddaa4b749a48dfa50252ccde419";

    private final String TAG = "gss.guru";
    private OkHttpClient client = new OkHttpClient();
    private GetNewsTask task = null;


    private static final String NEWS_AUTHOR = "news_author";
    private static final String NEWS_TITLE = "news_title";
    private String author, title;

    private OnFragmentInteractionListener mListener;
    public interface OnFragmentInteractionListener {
        void openDrover();
    }

    private AdapterNewsFeed adapterNewsFeed;
    private ProgressBar progress;
    private RecyclerView recyclerView;
    private LinearLayout fl_items_not_found;
    private SwipeRefreshLayout refresh_view;

    public FragmentNewsFeed() {
    }

    public static FragmentNewsFeed newInstance(String author, String title) {
        FragmentNewsFeed fragment = new FragmentNewsFeed();
        Bundle args = new Bundle();
        args.putString(NEWS_AUTHOR, author);
        args.putString(NEWS_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            author = getArguments().getString(NEWS_AUTHOR);
            title = getArguments().getString(NEWS_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.f_news, container, false);

        progress = v.findViewById(R.id.progress);
        refresh_view = v.findViewById(R.id.refresh_view);
        recyclerView = v.findViewById(R.id.recyclerView);
        fl_items_not_found = v.findViewById(R.id.fl_items_not_found);

        refresh_view.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListNews();
            }
        });

        adapterNewsFeed = new AdapterNewsFeed(getContext());
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_news_animation);
        recyclerView.setLayoutAnimation(animation);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterNewsFeed);

        progress.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        fl_items_not_found.setVisibility(View.GONE);

        Toolbar mToolbar = v.findViewById(R.id.toolbar);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        mToolbar.setNavigationIcon(R.drawable.ic_menu);
        mToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorIcons), PorterDuff.Mode.SRC_ATOP);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.openDrover();
                }
            }
        });

        mToolbar.setTitle(String.valueOf(title));
        mToolbar.setTitleTextColor(getResources().getColor(R.color.colorIcons));
        AppBarLayout app_bar = (AppBarLayout)v.findViewById(R.id.app_bar);
        app_bar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                mToolbar.setAlpha(1.5f - ((float) Math.abs(verticalOffset) / ((float) appBarLayout.getTotalScrollRange() / 3)));
            }
        });

        getListNews();
        return v;
    }

    private void getListNews(){
        if (task == null) {
            task = new GetNewsTask(author);
            task.execute((Void) null);
        }
    }

    public void setListNews(ArrayList<ModelNewsFeed> list) {
        if (list.size() == 0) {
            if (fl_items_not_found.getVisibility() != View.VISIBLE) {
                showContentAnimation(fl_items_not_found, progress);
            }
        } else {
            adapterNewsFeed.addAll(list);
            showContentAnimation(recyclerView, progress);
        }
        hideRefreshView(refresh_view);
    }

    public void setEmptyList() {
        hideRefreshView(refresh_view);
    }

    public void setError() {
        hideRefreshView(refresh_view);
        DialigError mDialigError = DialigError.newInstance();
        mDialigError.registerInterfaceCallback(new DialigError.InterfaceCallback() {
            @Override
            public void refresh() {

                getListNews();
            }

            @Override
            public void exit() {
                Objects.requireNonNull(getActivity()).finish();
            }
        });
        mDialigError.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), mDialigError.getClass().getSimpleName());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void showContentAnimation(final View newView, final View oldView) {
        final AlphaAnimation newViewAnimation = new AlphaAnimation(0.0f, 1.0f);
        AlphaAnimation oldViewAnimation = new AlphaAnimation(1.0f, 0.0f);
        newViewAnimation.setDuration(250);
        oldViewAnimation.setDuration(250);
        oldView.startAnimation(oldViewAnimation);
        oldViewAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                oldView.setVisibility(View.GONE);
                newView.setVisibility(View.VISIBLE);
                newView.startAnimation(newViewAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    public void hideRefreshView(SwipeRefreshLayout refresh_view) {
        if (refresh_view.isShown()) {
            refresh_view.setRefreshing(false);
        }
    }

    public class GetNewsTask extends AsyncTask<Void, Void, ArrayList<ModelNewsFeed>> {

        private String url;

        GetNewsTask(String author) {
            url = URL + "v1/articles?source=" + author + "&sortBy=top&apiKey=" + API_KEY;
            Log.d(TAG, "url = " + url);
        }

        @Override
        protected ArrayList<ModelNewsFeed> doInBackground(Void... params) {

            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (response.body() != null) {
                    try {
                        String body = response.body().string();
                        Log.d(TAG, "UserLoginTask.doInBackground response.body() = " + body);
                        ArrayList<ModelNewsFeed> list = new ArrayList<>();
                        try {
                            JSONObject argJSON = new JSONObject(body);
                            String status = argJSON.getString("status");
                            if(status != null && status.equals("ok")){
                                String articles = argJSON.getString("articles");
                                JSONArray listJSON = new JSONArray(articles);
                                for (int i = 0; i < listJSON.length(); i++){
                                    String object = listJSON.get(i).toString();
                                    JSONObject jsonObject = new JSONObject(object);
                                    String title = jsonObject.getString("title");
                                    String description = jsonObject.getString("description");
                                    String url = jsonObject.getString("url");
                                    String urlToImage = jsonObject.getString("urlToImage");
                                    String publishedAt = jsonObject.getString("publishedAt");
                                    ModelNewsFeed modelNewsFeed = new ModelNewsFeed(title, description, url, urlToImage, publishedAt);
                                    list.add(modelNewsFeed);
                                }
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "UserLoginTask.doInBackground JSONException", e);
                        }
                        return list;
                    } catch (IOException e) {
                        Log.e(TAG, "UserLoginTask.doInBackground", e);
                    }
                } else {
                    Log.e(TAG, "UserLoginTask.doInBackground response.body() == null");
                }
                return null;
            } catch (IOException e) {
                Log.e(TAG, "UserLoginTask.doInBackground", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(final ArrayList<ModelNewsFeed> list) {
            if(list == null){
                setEmptyList();
            } else {
                setListNews(list);
            }
        }

        @Override
        protected void onCancelled() {
            setError();
        }
    }
}