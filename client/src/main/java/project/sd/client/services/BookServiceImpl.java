package project.sd.client.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.sd.client.api.BookApi;
import project.sd.client.dto.AccountDto;
import project.sd.client.dto.BookDto;
import project.sd.client.dto.RatingDto;
import project.sd.client.util.SingletonRetrofit;
import retrofit2.Callback;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService{

    private BookApi bookApi = SingletonRetrofit.getInstance().getRetrofit().create(BookApi.class);

    @Override
    public void findAllBooks(Callback<List<BookDto>> callback) {
        bookApi.getBooks().enqueue(callback);
    }

    @Override
    public void insertBook(String title, String author, List<String> subjectList, String description, String edition,
                           Integer pages, Float price, Callback<BookDto> callback) {
        bookApi.createBook(title, author, subjectList, description, edition, pages, price).enqueue(callback);
    }

    @Override
    public void deleteBook(Integer id, Callback<BookDto> callback) {
        bookApi.deleteBook(id).enqueue(callback);
    }

    @Override
    public void updateBook(Integer id, String title, String author, List<String> subjectList, String description, String edition,
                           Integer pages, Float price, String status, Callback<BookDto> callback) {
        bookApi.updateBook(id, title, author, subjectList, description, edition, pages, price, status).enqueue(callback);
    }

    @Override
    public void findBooksByAuthor(String author, Callback<List<BookDto>> callback) {
        bookApi.searchByAuthor(author).enqueue(callback);
    }

    @Override
    public void findBooksByTitle(String title, Callback<List<BookDto>> callback) {
        bookApi.searchByTitle(title).enqueue(callback);
    }

    @Override
    public void findBooksBySubject(String subject, Callback<List<BookDto>> callback) {
        bookApi.searchBySubject(subject).enqueue(callback);
    }

    @Override
    public void findBooksByTime(String username, String time, Callback<List<BookDto>> callback) {
        bookApi.searchByTime(username, time).enqueue(callback);
    }

    @Override
    public void getFavouriteBooks(String username, Callback<List<BookDto>> callback) {
        bookApi.getFavouriteBooks(username).enqueue(callback);
    }

    @Override
    public void addToFavourites(String title, String username, Callback<BookDto> callback) {
        bookApi.addToFavourites(title, username).enqueue(callback);
    }

    @Override
    public void removeFromFavourites(String title, String username, Callback<BookDto> callback) {
        bookApi.removeFromFavourites(title, username).enqueue(callback);
    }

    @Override
    public void returnBook(String title, String username, String returnState, Callback<AccountDto> callback) {
        bookApi.returnBook(title, username, returnState).enqueue(callback);
    }

    @Override
    public void putInQueue(String title, String username, Callback<AccountDto> callback) {
        bookApi.putInQueue(title, username).enqueue(callback);
    }

    @Override
    public void rateBook(String title, String username, Double rating, Callback<RatingDto> callback) {
        bookApi.rateBook(title, username, rating).enqueue(callback);
    }
}
