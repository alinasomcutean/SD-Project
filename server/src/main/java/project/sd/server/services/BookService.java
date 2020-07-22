package project.sd.server.services;
import project.sd.server.dto.AccountDto;
import project.sd.server.dto.BookDto;
import project.sd.server.dto.BorrowDto;
import project.sd.server.dto.RatingDto;

import java.io.IOException;
import java.util.List;

public interface BookService {

    List<BookDto> findAllBooks();

    BookDto insertBook(String title, String author, List<String> subjectList, String description,
                       String edition, Integer pages, Float price);

    BookDto deleteBook(Integer id);

    BookDto updateBook(Integer id, String title, String author, List<String> subjectList, String description,
                       String edition, Integer pages, Float price, String status);

    List<BookDto> findBooksByAuthor(String author);

    List<BookDto> findBooksByTitle(String title);

    List<BookDto> findBookBySubject(String subject);

    List<BookDto> findBookByTime(List<BorrowDto> borrowDTOS, String userDays);

    BookDto borrowBook(Integer bookId, String accountUsername);

    List<BookDto> findFavouriteBooks(String username);

    BookDto addToFavourites(String title, String username);

    BookDto removeFromFavourites(String title, String username);

    AccountDto returnBook(String title, String username, String returnState);

    AccountDto putInQueue(String title, String username);

    RatingDto rateBook(String title, String username, Double rating);
}
