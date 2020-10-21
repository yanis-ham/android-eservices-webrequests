package android.eservices.webrequests.data.api;

import android.eservices.webrequests.data.api.model.BookSearchResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookDisplayService {
    @GET("books")
    Single<BookSearchResponse> searchBooks(@Query("q") String keywords, @Query("key") String apiKey);
}
