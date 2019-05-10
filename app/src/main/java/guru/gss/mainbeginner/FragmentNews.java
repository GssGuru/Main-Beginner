package guru.gss.mainbeginner;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import guru.gss.mainbeginner.model.NewsModel;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FragmentNews extends Fragment {

    private final String URL = "https://gss.guru/api/authorization";
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

    private AdapterNews adapterNews;
    private ProgressBar progress;
    private RecyclerView recyclerView;
    private LinearLayout fl_items_not_found;
    private SwipeRefreshLayout refresh_view;

    public FragmentNews() {
    }

    public static FragmentNews newInstance(String author, String title) {
        FragmentNews fragment = new FragmentNews();
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
//                presenter.getNewsList(author);
            }
        });

        adapterNews = new AdapterNews(getContext());
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_news_animation);
        recyclerView.setLayoutAnimation(animation);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterNews);

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

//        presenter.getNewsList(author);

        return v;
    }

    boolean mUserVisibleHint;

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        mUserVisibleHint = menuVisible;
        if (menuVisible && isResumed()) {
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mUserVisibleHint) {
            if (adapterNews.getItemCount() == 0) {
//                presenter.getNewsList(author);
            }
        }
    }


    public void setListNews(ArrayList<NewsModel> list) {
        if (list.size() == 0) {
            if (fl_items_not_found.getVisibility() != View.VISIBLE) {
                showContentAnimation(fl_items_not_found, progress);
            }
        } else {
            adapterNews.addAll(list);
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
//                presenter.getNewsList(author);
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

    public class GetNewsTask extends AsyncTask<Void, Void, ArrayList<NewsModel>> {

        private final String mAuthor;

        GetNewsTask(String author) {
            mAuthor = author;
        }

        @Override
        protected ArrayList<NewsModel> doInBackground(Void... params) {

            Request request = new Request.Builder()
                    .url(URL)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (response.body() != null) {
                    try {
                        String body = response.body().string();
                        ArrayList<NewsModel> list = new ArrayList<>();
                        return list;
                    } catch (IOException e) {
                        Log.e(TAG, "UserLoginTask.doInBackground", e);
                    }
                } else {
                    Log.e(TAG, "UserLoginTask.doInBackground response.body() != null");
                }
                return null;
            } catch (IOException e) {
                Log.e(TAG, "UserLoginTask.doInBackground", e);
                return null;
            }














        }

        @Override
        protected void onPostExecute(final ArrayList<NewsModel> list) {

//            if (requestResult != null) {
//                try {
//                    JSONObject argJSON = new JSONObject(requestResult);
//                    String response = argJSON.getString("response");
//                    JSONObject responseJSON = new JSONObject(response);
//                    String status = responseJSON.getString("status");
//                    if (status.equals("saccess")) {
//                        saveEmailAndPasswd(mEmail, mPassword);
//                        Toast.makeText(LoginActivity.this, "Авторизация успешна", Toast.LENGTH_SHORT).show();
//                        /*
//                         * TODO
//                         * Поздровляю))) Ми залогинелись
//                         */
//                    }
//                } catch (JSONException e) {
//                    Log.e(TAG, "UserLoginTask.onPostExecute", e);
//                }
//            } else {
//                Toast.makeText(LoginActivity.this, "Ошибка авторизации", Toast.LENGTH_SHORT).show();
//            }
//            showLoadingDialog(false);
        }

        @Override
        protected void onCancelled() {
//            showLoadingDialog(false);
        }
    }

}