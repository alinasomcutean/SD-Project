package project.sd.client.services;

import project.sd.client.dto.AccountDto;
import project.sd.client.dto.BookDto;
import project.sd.client.dto.RatingDto;
import retrofit2.Callback;

import java.util.List;

public interface BookService {

    void findAllBooks(Callback<List<BookDto>> callback);

    void insertBook(String title, String author, List<String> subjectList, String description,
                       String edition, Integer pages, Float price, Callback<BookDto> callback);

    void deleteBook(Integer id, Callback<BookDto> callback);

    void updateBook(Integer id, String title, String author, List<String> subjectList, String description,
                       String edition, Integer pages, Float price, String status, Callback<BookDto> callback);

    void findBooksByAuthor(String author, Callback<List<BookDto>> callback);

    void findBooksByTitle(String title, Callback<List<BookDto>> callback);

    void findBooksBySubject(String subject, Callback<List<BookDto>> callback);

    void findBooksByTime(String username, String time, Callback<List<BookDto>> callback);

    void getFavouriteBooks(String username, Callback<List<BookDto>> callback);

    void addToFavourites(String title, String username, Callback<BookDto> callback);

    void removeFromFavourites(String title, String username, Callback<BookDto> callback);

    void returnBook(String title, String username, String returnState, Callback<AccountDto> callback);

    void putInQueue(String title, String username, Callback<AccountDto> callback);

    void rateBook(String title, String username, Double rating, Callback<RatingDto> callback);
}
