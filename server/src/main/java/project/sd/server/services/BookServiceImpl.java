package project.sd.server.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.sd.server.dto.AccountDto;
import project.sd.server.dto.BookDto;
import project.sd.server.dto.BorrowDto;
import project.sd.server.dto.RatingDto;
import project.sd.server.model.*;
import project.sd.server.repository.*;
import project.sd.server.webSocket.RandomTicker;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;
    private final AuthorRepo authorRepo;
    private final SubjectRepo subjectRepo;
    private final BorrowRepo borrowRepo;
    private final AccountRepo accountRepo;
    private final RatingRepo ratingRepo;

    private final RatingService ratingService;

    private final RandomTicker randomTicker;

    public List<BookDto> findAllBooks() {
        List<Book> bookList = bookRepo.findAll();

        List<BookDto> books = new ArrayList<>();
        for (Book book : bookList) {
            // Create BookDto
            BookDto bookDTO = new BookDto(book);

            // Find the rating of the book
            Double rating = ratingService.getBookRating(book.getId());
            bookDTO.setRating(rating);
            books.add(bookDTO);
        }
        return books;
    }

    private Author createAuthor(String author) {
        // Search for the given author in the database
        Author author1 = authorRepo.findByName(author);

        // If the author is a new one, add it in the database
        if (author1 == null) {
            Author a = Author.builder().name(author).build();
            authorRepo.save(a);
            return a;
        } else {
            return author1;
        }
    }

    private List<Subject> createSubjectList(List<String> subjectList) {
        // List of subjects
        List<Subject> subjects = new ArrayList<>();

        // Transform the subject list from string to Subject
        if (!subjectList.isEmpty()) {
            for (String s : subjectList) {
                Subject subject = subjectRepo.findByName(s);
                subjects.add(subject);
            }
        }

        return subjects;
    }

    public BookDto insertBook(String title, String author, List<String> subjectList, String description,
                              String edition, Integer pages, Float price) {
        // Create the new book
        Book book = Book.builder().title(title)
                .author(createAuthor(author))
                .description(description)
                .subject(createSubjectList(subjectList))
                .edition(edition)
                .pages(pages)
                .price(price)
                .status("available")
                .borrow(new ArrayList<Borrow>()).build();
        bookRepo.save(book);

        return new BookDto(book);
    }

    public BookDto deleteBook(Integer id) {
        Book book = bookRepo.findById(id).orElseThrow(RuntimeException::new);
        BookDto bookDto = new BookDto(book);
        bookRepo.deleteById(id);

        return bookDto;
    }

    public BookDto updateBook(Integer id, String title, String author, List<String> subjectList, String description,
                              String edition, Integer pages, Float price, String status) {
        // Find the book which has to be updated
        Book book = bookRepo.findById(id).orElseThrow(RuntimeException::new);

        // Create its author and subject list
        Author a = createAuthor(author);
        List<Subject> subjects = createSubjectList(subjectList);

        // Set the new values for the book
        book.setTitle(title);
        book.setAuthor(a);
        book.setDescription(description);
        book.setEdition(edition);
        book.setPages(pages);
        book.setPrice(price);

        if (!subjectList.isEmpty()) {
            book.setSubject(subjects);
        }

        if (status != null) {
            book.setStatus(status);
        }

        // Save the modifications
        bookRepo.save(book);

        return new BookDto(book);
    }

    public List<BookDto> findBooksByAuthor(String author) {
        // Find if exists an author with the given name
        Author a = authorRepo.findByNameContaining(author);
        List<BookDto> bookDTOS = new ArrayList<>();

        if (a != null) { // Author exists
            // Find all book of the given author
            List<Book> books = bookRepo.findByAuthor(a);
            for (Book b : books) {
                bookDTOS.add(new BookDto(b));
            }
        }

        return bookDTOS;
    }

    public List<BookDto> findBooksByTitle(String title) {
        // Find if exists a book with the given title
        List<Book> books = bookRepo.findByTitleContaining(title);
        List<BookDto> bookDTOS = new ArrayList<>();

        if (books != null) { // Exists at least one book
            for (Book b : books) {
                bookDTOS.add(new BookDto(b));
            }
        }

        return bookDTOS;
    }

    public List<BookDto> findBookBySubject(String subject) {
        // Find if exists the given subject
        Subject s = subjectRepo.findByNameContaining(subject);
        List<BookDto> bookDTOS = new ArrayList<>();

        if (s != null) { // Subject exists
            // Find books from that subject
            List<Book> books = bookRepo.findBySubjectContaining(s);
            for (Book b : books) {
                bookDTOS.add(new BookDto(b));
            }
        }

        return bookDTOS;
    }

    public List<BookDto> findBookByTime(List<BorrowDto> borrowDTOS, String userDays) {
        List<BookDto> books = new ArrayList<>();

        if (!borrowDTOS.isEmpty()) {
            // Go through the borrow list of the logged in user
            for (BorrowDto borrow : borrowDTOS) {
                // Find the due date
                LocalDate dueDate = LocalDate.parse(borrow.getDueDate());

                // Find the number of days between current date and due date
                long days = ChronoUnit.DAYS.between(LocalDate.now(), dueDate);

                // If the difference is equal with the number of days given by user
                if (Long.parseLong(userDays) <= days) {
                    // Add the book in the final list
                    Borrow b = borrowRepo.findById(borrow.getId()).orElseThrow(RuntimeException::new);
                    books.add(new BookDto(b.getBook()));
                }
            }
        }

        return books;
    }

    public BookDto borrowBook(Integer bookId, String accountUsername) {
        // Find the book for which it has to be created a borrow
        Book book = bookRepo.findById(bookId).orElseThrow(RuntimeException::new);

        // Find the account which does the borrowing
        Account account = accountRepo.findByUsername(accountUsername);

        // Find the date of the borrowing
        LocalDate borrowDate = LocalDate.now();

        // Compute the due date
        LocalDate dueDate = LocalDate.now().plus(15, ChronoUnit.DAYS);

        // Create the borrow object and save it
        Borrow borrow = Borrow.builder().borrowedDate(borrowDate.toString())
                .dueDate(dueDate.toString())
                .book(book).build();
        borrowRepo.save(borrow);

        // Add it to the account
        List<Borrow> list = account.getBorrowList();
        list.add(borrow);
        account.setBorrowList(list);

        //Subtract the total number of books which can be borrowed at the same time
        Integer borrowsNo = account.getBorrowsNo();
        borrowsNo--;
        account.setBorrowsNo(borrowsNo);
        accountRepo.save(account);

        //Change book status
        book.setStatus("borrowed");
        bookRepo.save(book);
        return new BookDto(book);
    }

    @Override
    public List<BookDto> findFavouriteBooks(String username) {
        Account account = accountRepo.findByUsername(username);

        //Get all the favourite books for an account
        List<Book> books = account.getFavouriteBooks();

        //Transform from Book to BookDto
        List<BookDto> favouriteBooks = new ArrayList<>();
        books.forEach(b -> favouriteBooks.add(new BookDto(b)));

        return favouriteBooks;
    }

    @Override
    public BookDto addToFavourites(String title, String username) {
        Book book = bookRepo.findByTitle(title);
        Account account = accountRepo.findByUsername(username);

        //Find currently list for favourite books
        List<Book> favouriteBooks = account.getFavouriteBooks();

        //Check if the selected book is already in the favourite list
        boolean bookExists = false;
        for (Book b : favouriteBooks) {
            if (b.equals(book)) {
                bookExists = true;
                break;
            }
        }

        if (!bookExists) {
            //Add the new selected one
            favouriteBooks.add(book);
            account.setFavouriteBooks(favouriteBooks);
            accountRepo.save(account);
        }

        return new BookDto(book);
    }

    @Override
    public BookDto removeFromFavourites(String title, String username) {
        Account account = accountRepo.findByUsername(username);
        Book book = bookRepo.findByTitle(title);

        //Find currently list for favourite books
        List<Book> favouriteBooks = account.getFavouriteBooks();

        //Remove the selected book from list
        favouriteBooks.removeIf(b -> b.equals(book));

        //Save the new list
        account.setFavouriteBooks(favouriteBooks);
        accountRepo.save(account);

        return new BookDto(book);
    }

    @Override
    public AccountDto returnBook(String title, String username, String returnState) {
        Book book = bookRepo.findByTitle(title);
        Account account = accountRepo.findByUsername(username);
        Integer penalty = account.getPenalty();
        Integer borrowsNo = account.getBorrowsNo();

        //Find the last borrow of a user for a specific book
        List<Borrow> borrows = account.getBorrowList();
        List<Borrow> borrowsForBook = new ArrayList<>();
        for (Borrow b : borrows) {
            if (b.getBook().getId().equals(book.getId())) {
                borrowsForBook.add(b);
            }
        }

        Borrow b = borrowsForBook.get(borrowsForBook.size() - 1);
        LocalDate dueDate = LocalDate.parse(b.getDueDate());

        //Check if the user return the book later
        if (dueDate.isBefore(LocalDate.now())) {
            //If yes, accumulate a penalty to the account
            penalty++;
        }

        //Save the return state of the book
        b.setReturnState(returnState);
        borrowRepo.save(b);

        //Check the state in which the user return the book
        if (returnState.equals("Damaged") || returnState.equals("Used")) {
            penalty++;
        }

        //If the user accumulate 5 penalty points
        if(penalty >= 5) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 30);

            //A notification is send that the account is blocked.
            try{
                randomTicker.notify(account.getUsername(), "Blocked: " + calendar.getTime().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            account.setAccountBlocked(true);
        }

        //Save the new account penalty
        account.setPenalty(penalty);
        borrowsNo++;
        account.setBorrowsNo(borrowsNo);
        accountRepo.save(account);

        //Save the new status for the book
        book.setStatus("available");
        bookRepo.save(book);

        Account waitingAccount;
        //If the returned book has a waiting list
        if (!book.getWaitingList().isEmpty()) {
            //Find the next account which can borrow the book
            waitingAccount = book.getWaitingList().get(0);

            //Send a notification that the book is available and can be borrowed
            try {
                randomTicker.notify(waitingAccount.getUsername(), "Book available now: " + book.getTitle());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new AccountDto(account);
    }

    @Override
    public AccountDto putInQueue(String title, String username) {
        Book book = bookRepo.findByTitle(title);
        Account account = accountRepo.findByUsername(username);

        //Find the current waiting list of the book
        List<Account> waitingList = book.getWaitingList();

        //Add a new account to the waiting list
        waitingList.add(account);
        accountRepo.save(account);
        return new AccountDto(account);
    }

    @Override
    public RatingDto rateBook(String title, String username, Double rating) {
        Book book = bookRepo.findByTitle(title);
        Account account = accountRepo.findByUsername(username);

        //Create a new rating for the book
        Rating rating1 = Rating.builder().rating(rating).book(book).account(account).build();
        ratingRepo.save(rating1);

        return new RatingDto(rating1);
    }
}
