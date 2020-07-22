package project.sd.client.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import project.sd.client.dto.AccountDto;
import project.sd.client.dto.BookDto;
import project.sd.client.dto.BorrowDto;
import project.sd.client.dto.PersonDto;
import project.sd.client.services.BookService;
import project.sd.client.services.BorrowService;
import project.sd.client.util.AlertBox;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;


@RequiredArgsConstructor
@RestController
@FxmlView("borrows.fxml")
public class BorrowsController {

    private final BorrowService borrowService;
    private final BookService bookService;

    @FXML
    private TableView<BorrowDto> tableViewBorrows;
    @FXML
    private TableColumn<BorrowDto, String> title;
    @FXML
    private TableColumn<BorrowDto, String> author;
    @FXML
    private TableColumn<BorrowDto, String> borrowDate;
    @FXML
    private TableColumn<BorrowDto, String> dueDate;
    @FXML
    private TableColumn<BorrowDto, String> returnState;

    @FXML
    private Label borrowsUser;
    @FXML
    private Label subscriptionsAccount;

    @FXML
    private TextField subscriptionsTextField;
    @FXML private TextField borrow;
    @FXML private ComboBox<String> chooseReturnState;

    private PersonDto getCurrentUser() {
        return (PersonDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public void initialize() {
        PersonDto personDto = getCurrentUser();
        borrowsUser.setText("Borrows for user: " + personDto.getFirstName() + " " + personDto.getLastName());
        subscriptionsAccount.setText("Number of subscriptions: " + personDto.getAccount().getBorrowsNo());

        borrowService.getBorrowsByAccount(personDto.getAccount().getUsername(), new Callback<List<BorrowDto>>() {
            @Override
            public void onResponse(Call<List<BorrowDto>> call, Response<List<BorrowDto>> response) {
                if (response.isSuccessful()) {
                    Platform.runLater(() -> {
                        ObservableList<BorrowDto> borrowList = FXCollections.observableArrayList(response.body());
                        tableViewBorrows.setItems(borrowList);

                        title.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getTitle()).asString());
                        author.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getAuthor()).asString());
                        borrowDate.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getBorrowedDate()).asString());
                        dueDate.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDueDate()).asString());
                        returnState.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getReturnState()).asString());
                    });
                }
            }

            @Override
            public void onFailure(Call<List<BorrowDto>> call, Throwable throwable) {

            }
        });
    }

    public void editSubscriptions() {
        borrowService.editSubscriptions(getCurrentUser().getAccount().getUsername(), subscriptionsTextField.getText(), new Callback<AccountDto>() {
            @Override
            public void onResponse(Call<AccountDto> call, Response<AccountDto> response) {
                Platform.runLater(() -> {
                    subscriptionsAccount.setText("Number of subscriptions: " + subscriptionsTextField.getText());
                });
            }

            @Override
            public void onFailure(Call<AccountDto> call, Throwable throwable) {

            }
        });
    }

    public void borrowBook() {
        PersonDto personDto = getCurrentUser();
        if(personDto.getAccount().getBorrowsNo() > 0) {
            borrowService.borrowBook(Integer.parseInt(borrow.getText()), personDto.getAccount().getUsername(), new Callback<BookDto>() {
                @Override
                public void onResponse(Call<BookDto> call, Response<BookDto> response) {
                    Platform.runLater(() -> {
                        AlertBox.display("Borrow book", "Book was successfully borrowed!");
                        tableViewBorrows.refresh();
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
    }

    public void returnBook() {
        String bookTitle = tableViewBorrows.getSelectionModel().getSelectedItem().getTitle();
        PersonDto personDto = getCurrentUser();

        bookService.returnBook(bookTitle, personDto.getAccount().getUsername(), chooseReturnState.getValue(), new Callback<AccountDto>() {
            @Override
            public void onResponse(Call<AccountDto> call, Response<AccountDto> response) {
                Platform.runLater(() -> {
                    AlertBox.display("Return book", "Book " + bookTitle + " was successfully returned");
                });
            }

            @Override
            public void onFailure(Call<AccountDto> call, Throwable throwable) {

            }
        });
    }
}
