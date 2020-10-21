package android.eservices.webrequests.data.repository.bookdisplay;

import android.eservices.webrequests.data.api.model.BookSearchResponse;
import android.eservices.webrequests.data.repository.bookdisplay.remote.BookDisplayRemoteDataSource;

import io.reactivex.Single;

public class BookDisplayDataRepository implements BookDisplayRepository {

    private BookDisplayRemoteDataSource bookDisplayRemoteDataSource;

    public BookDisplayDataRepository(BookDisplayRemoteDataSource bookDisplayRemoteDataSource) {
        this.bookDisplayRemoteDataSource = bookDisplayRemoteDataSource;
    }

    @Override
    public Single<BookSearchResponse> getBookSearchResponse(String keywords) {
        return bookDisplayRemoteDataSource.getBookSearchResponse(keywords);
    }
}
