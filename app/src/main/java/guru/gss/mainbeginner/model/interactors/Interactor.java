package guru.gss.mainbeginner.model.interactors;

import guru.gss.mainbeginner.model.interactors.news.interfaces.OnFinishedListener;

public interface Interactor {

    interface InteractorNews {

        void getList(String author, OnFinishedListener listener);
    }
}
