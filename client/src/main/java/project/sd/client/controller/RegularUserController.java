package project.sd.client.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.controlsfx.control.Rating;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import project.sd.client.JavaFxApplication;
import project.sd.client.dto.*;
import project.sd.client.services.BookService;
import project.sd.client.services.BorrowService;
import project.sd.client.services.RatingService;
import project.sd.client.services.ReviewService;
import project.sd.client.util.AlertBox;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

@RequiredArgsConstructor
@RestController
@FxmlView("regularUser.fxml")
public class RegularUserController {

    private final BookService bookService;
    private final BorrowService borrowService;
    private final RatingService ratingService;
    private final ReviewService reviewService;

    // Columns for book's table
    @FXML private TableView<BookDto> tableViewBooks;
    @FXML private TableColumn<BookDto, String> title;
    @FXML private TableColumn<BookDto, String> author;
    @FXML private TableColumn<BookDto, String> description;
    @FXML private TableColumn<BookDto, String> subject;
    @FXML private TableColumn<BookDto, String> edition;
    @FXML private TableColumn<BookDto, String> status;
    @FXML private TableColumn<BookDto, String> pages;
    @FXML private TableColumn<BookDto, String> price;
    @FXML private TableColumn<BookDto, String> ratingColumn;

    // Columns for borrow's table
    @FXML private TableView<BorrowDto> tableViewBorrow;
    @FXML private TableColumn<BorrowDto, String> borrowDate;
    @FXML private TableColumn<BorrowDto, String> dueDate;

    // Columns for favourite's table
    @FXML private TableView<BookDto> tableViewFavourites;
    @FXML private TableColumn<BookDto, String> favouriteTitle;
    @FXML private TableColumn<BookDto, String>  favouriteAuthor;

    // Text fields for search
    @FXML private TextField searchTextField;
    @FXML public ComboBox<String> searchCriteria;

    @FXML public ComboBox<String> returnState;

    @FXML private Label penalty;
    @FXML private Label loggedAccount;
    @FXML private Rating rating;
    @FXML private Button rateBook;
    @FXML private Button borrowBook;

    @FXML private TextArea review;

    public String message;

    private AccountDto getCurrentUser() {
        return (AccountDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public void initialize() {
        AccountDto accountDto = getCurrentUser();
        penalty.setText("Penalty: " + accountDto.getPenalty().toString());
        if(accountDto.isAccountBlocked()) {
            borrowBook.setDisable(true);
        }
        loggedAccount.setText("Logged in account: " + accountDto.getUsername());
    }

    public void viewBooks() {
        bookService.findAllBooks(new Callback<List<BookDto>>() {
            @Override
            public void onResponse(@NotNull Call<List<BookDto>> call, @NotNull Response<List<BookDto>> response) {
                if(response.isSuccessful()){
                    ObservableList<BookDto> bookList = FXCollections.observableArrayList(response.body());
                    tableViewBooks.setItems(bookList);

                    title.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getTitle()).asString());
                    author.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getAuthor()).asString());
                    description.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDescription()).asString());
                    subject.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getAllSubjects()).asString());
                    edition.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getEdition()).asString());
                    pages.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPages()).asString());
                    price.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPrice()).asString());
                    status.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getStatus()).asString());
                    ratingColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getRating()).asString());
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<BookDto>> call, @NotNull Throwable throwable) {

            }
        });
    }

    public void viewBorrow() {
        AccountDto currentAccount = getCurrentUser();

        // Get selected book to view its borrows
        BookDto selectedBook = tableViewBooks.getSelectionModel().getSelectedItem();

        borrowService.getBorrowsByBookIdAndAccount(currentAccount.getUsername(), selectedBook.getId(), new Callback<List<BorrowDto>>() {
            @Override
            public void onResponse(Call<List<BorrowDto>> call, Response<List<BorrowDto>> response) {
                if(response.isSuccessful()) {
                    Platform.runLater(() -> {
                        ObservableList<BorrowDto> borrowList = FXCollections.observableArrayList(response.body());
                        tableViewBorrow.setItems(borrowList);

                        borrowDate.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getBorrowedDate()));
                        dueDate.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDueDate()));
                    });
                } else {
                    Platform.runLater(() -> AlertBox.display("Borrows", "There are no borrows for book " + selectedBook.getTitle()));
                }
            }

            @Override
            public void onFailure(Call<List<BorrowDto>> call, Throwable throwable) {

            }
        });
    }

    public void borrowBook() {
        // Get the selected book
        BookDto bookDTO = tableViewBooks.getSelectionModel().getSelectedItem();

        //Get the current logged in user
        AccountDto currentAccount = getCurrentUser();

        if(bookDTO.getStatus().equals("available")) {
            if(currentAccount.getBorrowsNo() > 0) {
                borrowService.borrowBook(bookDTO.getId(), currentAccount.getUsername(), new Callback<BookDto>() {
                    @Override
                    public void onResponse(Call<BookDto> call, Response<BookDto> response) {
                        Platform.runLater(() -> {
                            AlertBox.display("Borrow book", "Book " + bookDTO.getTitle() + " was successfully borrowed!");
                            viewBorrow();
                            viewBooks();
                        });
                    }

                    @Override
                    public void onFailure(Call<BookDto> call, Throwable throwable) {

                    }
                });
            }
            else {
                Platform.runLater(() -> AlertBox.display("Bad Request", "You cannot borrow books anymore. You have too many books borrowed at this moment."));
            }
        } else{
            Platform.runLater(() -> AlertBox.display("Bad Request", "Book is not available and cannot be borrowed, but you can put yourself in queue."));
        }
    }

    private void searchByRating() {
        String rating = searchTextField.getText();
        if(rating.startsWith("<")) {
            Double r = Double.parseDouble(rating.substring(1));
            ratingService.ratingLessThan(r, new Callback<List<BookDto>>() {
                @Override
                public void onResponse(Call<List<BookDto>> call, Response<List<BookDto>> response) {
                    if(!response.body().isEmpty()) {
                        ObservableList<BookDto> bookList = FXCollections.observableArrayList(response.body());
                        tableViewBooks.setItems(bookList);
                    } else{
                        Platform.runLater(() -> AlertBox.display("Search book", "There are no books with rating less than " + r));
                    }
                }

                @Override
                public void onFailure(Call<List<BookDto>> call, Throwable throwable) {

                }
            });
        } else {
            if(rating.startsWith(">")) {
                Double r = Double.parseDouble(rating.substring(1));
                ratingService.ratingGreaterThan(r, new Callback<List<BookDto>>() {
                    @Override
                    public void onResponse(Call<List<BookDto>> call, Response<List<BookDto>> response) {
                        if(!response.body().isEmpty()) {
                            ObservableList<BookDto> bookList = FXCollections.observableArrayList(response.body());
                            tableViewBooks.setItems(bookList);
                        } else{
                            Platform.runLater(() -> AlertBox.display("Search book", "There are no books with rating greater than " + r));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<BookDto>> call, Throwable throwable) {

                    }
                });
            } else {
                if(rating.startsWith("=")) {
                    Double r = Double.parseDouble(rating.substring(1));
                    ratingService.ratingEquals(r, new Callback<List<BookDto>>() {
                        @Override
                        public void onResponse(Call<List<BookDto>> call, Response<List<BookDto>> response) {
                            if(!response.body().isEmpty()) {
                                ObservableList<BookDto> bookList = FXCollections.observableArrayList(response.body());
                                tableViewBooks.setItems(bookList);
                            } else{
                                Platform.runLater(() -> AlertBox.display("Search book", "There are no books with rating equal to " + r));
                            }
                        }

                        @Override
                        public void onFailure(Call<List<BookDto>> call, Throwable throwable) {

                        }
                    });
                } else {
                    if(rating.contains("-")) {
                        Double minRating = Double.parseDouble(rating.substring(0, 1));
                        Double maxRating = Double.parseDouble(rating.substring(2, 3));
                        ratingService.ratingBetween(minRating, maxRating, new Callback<List<BookDto>>() {
                            @Override
                            public void onResponse(Call<List<BookDto>> call, Response<List<BookDto>> response) {
                                if(!response.body().isEmpty()) {
                                    ObservableList<BookDto> bookList = FXCollections.observableArrayList(response.body());
                                    tableViewBooks.setItems(bookList);
                                } else{
                                    Platform.runLater(() -> AlertBox.display("Search book", "There are no books with rating between " + minRating + " and " + maxRating));
                                }
                            }

                            @Override
                            public void onFailure(Call<List<BookDto>> call, Throwable throwable) {

                            }
                        });
                    }
                }
            }
        }
    }

    public void searchBook() {
        if(searchCriteria.getValue().equals("Author")) {
            bookService.findBooksByAuthor(searchTextField.getText(), new Callback<List<BookDto>>() {
                @Override
                public void onResponse(Call<List<BookDto>> call, Response<List<BookDto>> response) {
                    if(response.isSuccessful()) {
                        ObservableList<BookDto> bookList = FXCollections.observableArrayList(response.body());
                        tableViewBooks.setItems(bookList);
                    } else{
                        Platform.runLater(() -> AlertBox.display("Search book", "There are no books written by " + searchTextField.getText() + "."));
                    }
                }

                @Override
                public void onFailure(Call<List<BookDto>> call, Throwable throwable) {

                }
            });
        } else{
            if(searchCriteria.getValue().equals("Title")) {
                bookService.findBooksByTitle(searchTextField.getText(), new Callback<List<BookDto>>() {
                    @Override
                    public void onResponse(Call<List<BookDto>> call, Response<List<BookDto>> response) {
                        if(response.isSuccessful()) {
                            ObservableList<BookDto> bookList = FXCollections.observableArrayList(response.body());
                            tableViewBooks.setItems(bookList);
                        } else{
                            Platform.runLater(() -> AlertBox.display("Search book", "There are no books with title " + searchTextField.getText() + "."));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<BookDto>> call, Throwable throwable) {

                    }
                });
            } else {
                if(searchCriteria.getValue().equals("Genre")) {
                    bookService.findBooksBySubject(searchTextField.getText(), new Callback<List<BookDto>>() {
                        @Override
                        public void onResponse(Call<List<BookDto>> call, Response<List<BookDto>> response) {
                            if (response.isSuccessful()) {
                                ObservableList<BookDto> bookList = FXCollections.observableArrayList(response.body());
                                tableViewBooks.setItems(bookList);
                            } else {
                                Platform.runLater(() -> AlertBox.display("Search book", "There are no books in this genre: " + searchTextField.getText() + "."));
                            }
                        }

                        @Override
                        public void onFailure(Call<List<BookDto>> call, Throwable throwable) {

                        }
                    });
                } else {
                    if(searchCriteria.getValue().equals("Time")) {
                        bookService.findBooksByTime(getCurrentUser().getUsername(), searchTextField.getText(), new Callback<List<BookDto>>() {
                            @Override
                            public void onResponse(Call<List<BookDto>> call, Response<List<BookDto>> response) {
                                if (response.isSuccessful()) {
                                    ObservableList<BookDto> bookList = FXCollections.observableArrayList(response.body());
                                    tableViewBooks.setItems(bookList);
                                } else {
                                    Platform.runLater(() -> AlertBox.display("Search book", "There are no books to return until " + searchTextField.getText() + "."));
                                }
                            }

                            @Override
                            public void onFailure(Call<List<BookDto>> call, Throwable throwable) {

                            }
                        });
                    } else {
                        if (searchCriteria.getValue().equals("Rating")) {
                            searchByRating();
                        } else {
                            Platform.runLater(() -> AlertBox.display("Search book", "You must complete a field in order to search for a book"));
                        }
                    }
                }
            }
        }
    }

    public void viewFavourites() {
        AccountDto accountDto = getCurrentUser();

        bookService.getFavouriteBooks(accountDto.getUsername(), new Callback<List<BookDto>>() {
            @Override
            public void onResponse(Call<List<BookDto>> call, Response<List<BookDto>> response) {
                if(response.isSuccessful()) {
                    Platform.runLater(() -> {
                        ObservableList<BookDto> favouriteBooks = FXCollections.observableArrayList(response.body());
                        tableViewFavourites.setItems(favouriteBooks);

                        favouriteTitle.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getTitle()));
                        favouriteAuthor.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getAuthor()));
                    });
                }
            }

            @Override
            public void onFailure(Call<List<BookDto>> call, Throwable throwable) {

            }
        });
    }

    public void addToFavourites() {
        BookDto selectedBook = tableViewBooks.getSelectionModel().getSelectedItem();
        AccountDto accountDto = getCurrentUser();
        bookService.addToFavourites(selectedBook.getTitle(), accountDto.getUsername(), new Callback<BookDto>() {
            @Override
            public void onResponse(Call<BookDto> call, Response<BookDto> response) {
                Platform.runLater(() -> {
                    AlertBox.display("Favourites", "Book " + selectedBook.getTitle() + " successfully added to the favourites list");
                    viewFavourites();
                });
            }

            @Override
            public void onFailure(Call<BookDto> call, Throwable throwable) {

            }
        });
    }

    public void removeFromFavourites() {
        BookDto selectedBook = tableViewFavourites.getSelectionModel().getSelectedItem();
        AccountDto accountDto = getCurrentUser();
        bookService.removeFromFavourites(selectedBook.getTitle(), accountDto.getUsername(), new Callback<BookDto>() {
            @Override
            public void onResponse(Call<BookDto> call, Response<BookDto> response) {
                Platform.runLater(() -> {
                    AlertBox.display("Favourites", "Book " + selectedBook.getTitle() + " successfully remove from the favourites list");
                    viewFavourites();
                });
            }

            @Override
            public void onFailure(Call<BookDto> call, Throwable throwable) {

            }
        });
    }

    public void returnBook() {
        BookDto selectedBook = tableViewBooks.getSelectionModel().getSelectedItem();
        AccountDto accountDto = getCurrentUser();

        if(selectedBook.getStatus().equals("available")) {
            Platform.runLater(() -> AlertBox.display("Return book", "Book is not borrowed"));
        } else {
            bookService.returnBook(selectedBook.getTitle(), accountDto.getUsername(), returnState.getValue(), new Callback<AccountDto>() {
                @Override
                public void onResponse(Call<AccountDto> call, Response<AccountDto> response) {
                    Platform.runLater(() -> {
                        AlertBox.display("Return book", "Book " + selectedBook.getTitle() + " was successfully returned");
                        penalty.setText("Penalty: " + response.body().getPenalty().toString());
                        viewBooks();
                        if(penalty.getText().equals("5")) {
                            borrowBook.setDisable(true);
                        }
                    });
                }

                @Override
                public void onFailure(Call<AccountDto> call, Throwable throwable) {

                }
            });
        }
    }

    public void putInQueue() {
        BookDto selectedBook = tableViewBooks.getSelectionModel().getSelectedItem();
        AccountDto accountDto = getCurrentUser();

        bookService.putInQueue(selectedBook.getTitle(), accountDto.getUsername(), new Callback<AccountDto>() {
            @Override
            public void onResponse(Call<AccountDto> call, Response<AccountDto> response) {
                Platform.runLater(() -> AlertBox.display("Borrow book", "You are now placed in the waiting list and you will receive a notification when it is available"));
            }

            @Override
            public void onFailure(Call<AccountDto> call, Throwable throwable) {

            }
        });

        /*Platform.runLater(() -> {
            BookObserver bookObserver = new BookObserver();
            bookObserver.addObserver((Observer) accountDto);
        });*/

    }

    public void selectBook() {
        BookDto bookDto = tableViewBooks.getSelectionModel().getSelectedItem();
        AccountDto accountDto = getCurrentUser();
        ratingService.getRatingByBookAndAccount(bookDto.getTitle(), accountDto.getUsername(), new Callback<RatingDto>() {
            @Override
            public void onResponse(Call<RatingDto> call, Response<RatingDto> response) {
                if(response.isSuccessful()) {
                    rateBook.setDisable(true);
                } else {
                    rateBook.setDisable(false);
                }
            }

            @Override
            public void onFailure(Call<RatingDto> call, Throwable throwable) {

            }
        });
    }

    public void rateBook() {
        Double ratingValue = rating.getRating();
        BookDto bookDto = tableViewBooks.getSelectionModel().getSelectedItem();
        AccountDto accountDto = getCurrentUser();
        bookService.rateBook(bookDto.getTitle(), accountDto.getUsername(), ratingValue, new Callback<RatingDto>() {
            @Override
            public void onResponse(Call<RatingDto> call, Response<RatingDto> response) {
                Platform.runLater(() -> {
                    AlertBox.display("Rating", "You rate book " + bookDto.getTitle() + " with " + ratingValue);
                    rateBook.setDisable(true);
                    viewBooks();
                });
            }

            @Override
            public void onFailure(Call<RatingDto> call, Throwable throwable) {

            }
        });
    }

    public void reviewBook() {
        String reviewText = review.getText();
        BookDto bookDto = tableViewBooks.getSelectionModel().getSelectedItem();
        AccountDto accountDto = getCurrentUser();

        reviewService.createReview(bookDto.getTitle(), accountDto.getUsername(), reviewText, new Callback<ReviewDto>() {
            @Override
            public void onResponse(Call<ReviewDto> call, Response<ReviewDto> response) {
                Platform.runLater(() -> {
                    AlertBox.display("Review", "Review succesfully added for book " + bookDto.getTitle());
                    review.clear();
                });
            }

            @Override
            public void onFailure(Call<ReviewDto> call, Throwable throwable) {

            }
        });
    }

    public void viewReviews() {
        JavaFxApplication.changeScene("reviews");
    }
}
