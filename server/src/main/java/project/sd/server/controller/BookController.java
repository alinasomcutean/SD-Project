package project.sd.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.sd.server.dto.AccountDto;
import project.sd.server.dto.BookDto;
import project.sd.server.dto.RatingDto;
import project.sd.server.dto.SubjectDto;
import project.sd.server.services.BookService;
import project.sd.server.services.BorrowService;
import project.sd.server.services.SubjectService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final SubjectService subjectService;
    private final BorrowService borrowService;

    @GetMapping("/books")
    public List<BookDto> getBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/genres")
    public List<SubjectDto> getSubjects() {
        return subjectService.findAllSubjects();
    }

    @PostMapping("/addBook")
    public BookDto createBook(@RequestParam String title, @RequestParam String author, @RequestParam List<String> subjectList,
                              @RequestParam String description, @RequestParam String edition, @RequestParam Integer pages, @RequestParam Float price) {
        return bookService.insertBook(title, author, subjectList, description, edition, pages, price);
    }

    @DeleteMapping("/deleteBook/{id}")
    public BookDto deleteBook(@PathVariable Integer id) {
        borrowService.deleteBorrowByBookId(id);
        return bookService.deleteBook(id);
    }

    @PostMapping("/updateBook")
    public BookDto updateBook(@RequestParam Integer id, @RequestParam String title, @RequestParam String author, @RequestParam List<String> subjectList,
                              @RequestParam String description, @RequestParam String edition, @RequestParam Integer pages, @RequestParam Float price,
                              @RequestParam String status) {
        return bookService.updateBook(id, title, author, subjectList, description, edition, pages, price, status);
    }

    @PostMapping("/favouriteBooks")
    public List<BookDto> getFavouriteBooks(@RequestParam String username) {
        return bookService.findFavouriteBooks(username);
    }

    @PostMapping("/addToFavourites")
    public BookDto addToFavourites(@RequestParam String title, @RequestParam String username) {
        return bookService.addToFavourites(title, username);
    }

    @PostMapping("/removeFromFavourites")
    public BookDto removeFromFavourites(@RequestParam String title, @RequestParam String username) {
        return bookService.removeFromFavourites(title, username);
    }

    @PostMapping("/returnBook")
    public AccountDto returnBook(@RequestParam String title, @RequestParam String username, @RequestParam String returnState) throws IOException {
        return bookService.returnBook(title, username, returnState);
    }

    @PostMapping("/putInQueue")
    public AccountDto putInQueue(@RequestParam String title, String username) {
        return bookService.putInQueue(title, username);
    }

    @PostMapping("/rating")
    public RatingDto rateBook(@RequestParam String title, @RequestParam String username, @RequestParam Double rating) {
        return bookService.rateBook(title, username, rating);
    }
}
