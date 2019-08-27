package android.eservices.webrequests.data.repository.bookdisplay;

import android.eservices.webrequests.data.api.model.Book;
import android.eservices.webrequests.data.api.model.BookSearchResponse;
import android.eservices.webrequests.data.entity.BookEntity;
import android.eservices.webrequests.data.repository.bookdisplay.local.SongDisplayLocalDataSource;
import android.eservices.webrequests.data.repository.bookdisplay.mapper.BookToBookEntityMapper;
import android.eservices.webrequests.data.repository.bookdisplay.remote.BookDisplayRemoteDataSource;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class BookDisplayDataRepository implements BookDisplayRepository {

    private SongDisplayLocalDataSource songDisplayLocalDataSource;
    private BookDisplayRemoteDataSource bookDisplayRemoteDataSource;
    private BookToBookEntityMapper bookToBookEntityMapper;

    public BookDisplayDataRepository(SongDisplayLocalDataSource songDisplayLocalDataSource,
                                     BookDisplayRemoteDataSource bookDisplayRemoteDataSource,
                                     BookToBookEntityMapper bookToBookEntityMapper) {
        this.songDisplayLocalDataSource = songDisplayLocalDataSource;
        this.bookDisplayRemoteDataSource = bookDisplayRemoteDataSource;
        this.bookToBookEntityMapper = bookToBookEntityMapper;
    }

    @Override
    public Single<BookSearchResponse> getBookSearchResponse(String keywords) {
        return bookDisplayRemoteDataSource.getBookSearchResponse(keywords)
                .zipWith(songDisplayLocalDataSource.getFavoriteIdList(), new BiFunction<BookSearchResponse, List<String>, BookSearchResponse>() {
                    @Override
                    public BookSearchResponse apply(BookSearchResponse bookSearchResponse, List<String> idList) throws Exception {
                        for (Book book : bookSearchResponse.getBookList()) {
                            if (idList.contains(book.getId())) {
                                book.setFavorite();
                            }
                        }
                        return bookSearchResponse;
                    }
                });
    }

    @Override
    public Flowable<List<BookEntity>> getFavoriteBooks() {
        return songDisplayLocalDataSource.loadFavorites();
    }

    @Override
    public Completable addBookToFavorites(String bookId) {
        return bookDisplayRemoteDataSource.getBookDetails(bookId)
                .map(new Function<Book, BookEntity>() {
                    @Override
                    public BookEntity apply(Book book) throws Exception {
                        return bookToBookEntityMapper.map(book);
                    }
                })
                .flatMapCompletable(new Function<BookEntity, CompletableSource>() {
                    @Override
                    public CompletableSource apply(BookEntity bookEntity) throws Exception {
                        return songDisplayLocalDataSource.addBookToFavorites(bookEntity);
                    }
                });
    }

    @Override
    public Completable removeBookFromFavorites(String bookId) {
        return songDisplayLocalDataSource.deleteBookFromFavorites(bookId);
    }
}
