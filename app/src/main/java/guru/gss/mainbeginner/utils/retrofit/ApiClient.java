package guru.gss.mainbeginner.utils.retrofit;

import guru.gss.mainbeginner.utils.model.NewsApiModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiClient {

    @GET("articles")
    Call<NewsApiModel> getNewsList(@Query("source") String source, @Query("sortBy") String sortBy, @Query("apiKey") String apiKey);

}
