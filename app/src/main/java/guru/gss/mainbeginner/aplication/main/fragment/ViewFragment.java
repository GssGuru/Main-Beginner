package guru.gss.mainbeginner.aplication.main.fragment;

import com.arellomobile.mvp.MvpView;

import java.util.ArrayList;

import guru.gss.mainbeginner.utils.model.NewsModel;

public interface ViewFragment extends MvpView {

    void setListNews(ArrayList<NewsModel> list);
    void setEmptyList();
    void setError();
}
