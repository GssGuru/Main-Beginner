package guru.gss.mainbeginner.model.repository.network;

import guru.gss.mainbeginner.model.interactors.news.interfaces.OnFinishedListener;

public interface NetworkRepository {

    void getNews(OnFinishedListener listener, String author, String key);

}
