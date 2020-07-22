package project.sd.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.sd.server.dto.BookDto;
import project.sd.server.dto.BorrowDto;
import project.sd.server.services.BookService;
import project.sd.server.services.BorrowService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RegularUserController {

    private final BorrowService borrowService;
    private final BookService bookService;

    @PostMapping("/borrows")
    public List<BorrowDto> getBorrowsByBookIdAndAccount(@RequestParam String username, @RequestParam Integer bookId) {
        return borrowService.viewBorrowsByBookIdAndAccount(username, bookId);
    }

    @PostMapping("/borrowBook")
    public BookDto borrowBook(@RequestParam Integer bookId, @RequestParam String accountUsername) {
        return bookService.borrowBook(bookId, accountUsername);
    }

    @GetMapping("/searchByAuthor/{authorName}")
    public List<BookDto> searchByAuthor(@PathVariable String authorName) {
        return bookService.findBooksByAuthor(authorName);
    }

    @GetMapping("/searchByTitle/{title}")
    public List<BookDto> searchByTitle(@PathVariable String title) {
        return bookService.findBooksByTitle(title);
    }

    @GetMapping("/searchBySubject/{subject}")
    public List<BookDto> searchBySubject(@PathVariable String subject) {
        return bookService.findBookBySubject(subject);
    }

    @PostMapping("/searchByTime")
    public List<BookDto> searchByTime(@RequestParam String username, @RequestParam String time) {
        List<BorrowDto> borrowDtos = borrowService.viewBorrowsByAccount(username);
        return bookService.findBookByTime(borrowDtos, time);
    }

    @GetMapping("/borrows/{username}")
    public List<BorrowDto> getBorrowsByAccount(@PathVariable("username") String username) {
        return borrowService.viewBorrowsByAccount(username);
    }
}
