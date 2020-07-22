package project.sd.client.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.RestController;
import project.sd.client.JavaFxApplication;
import project.sd.client.dto.BookDto;
import project.sd.client.dto.SubjectDto;
import project.sd.client.services.BookService;
import project.sd.client.services.SubjectService;
import project.sd.client.util.AlertBox;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RestController
@FxmlView("books.fxml")
public class BookController {

    @Inject
    private BookService bookService;

    @Inject
    private SubjectService subjectService;

    // Table columns
    @FXML
    private TableView<BookDto> tableViewBooks;
    @FXML private TableColumn<BookDto, String> bookId;
    @FXML private TableColumn<BookDto, String> title;
    @FXML private TableColumn<BookDto, String> author;
    @FXML private TableColumn<BookDto, String> description;
    @FXML private TableColumn<BookDto, String> subject;
    @FXML private TableColumn<BookDto, String> edition;
    @FXML private TableColumn<BookDto, String> status;
    @FXML private TableColumn<BookDto, String> pages;
    @FXML private TableColumn<BookDto, String> price;
    @FXML private TableColumn<BookDto, String> rating;

    @FXML private TextField updateTitle;
    @FXML private TextField updateAuthor;
    @FXML private TextField updateDescription;
    @FXML private TextField updateEdition;
    @FXML private TextField updatePages;
    @FXML private TextField updatePrice;

    @FXML public ComboBox<String> updateSubjectsList;
    @FXML public ComboBox<String> updateStatus;

    private List<String> subjects = new ArrayList<>();
    private BookDto selectedBook;

    public void initialize() {
        subjectService.findAllSubjects(new Callback<List<SubjectDto>>() {
            @Override
            public void onResponse(Call<List<SubjectDto>> call, Response<List<SubjectDto>> response) {
                Platform.runLater(() -> {
                    // Find the list with the subjects
                    List<SubjectDto> list = response.body();

                    // Create a list of strings with the name of the subjects
                    List<String> sList = new ArrayList<>();
                    for (SubjectDto subjectDTO : list) {
                        sList.add(subjectDTO.getName());
                    }

                    // Create a list to add to the combo box
                    ObservableList<String> strings =
                            FXCollections.observableArrayList(FXCollections.observableArrayList(sList));
                    updateSubjectsList.setItems(strings);
                });
            }

            @Override
            public void onFailure(Call<List<SubjectDto>> call, Throwable throwable) {

            }
        });

    }

    public void viewBooks() {
        bookService.findAllBooks(new Callback<List<BookDto>>() {
            @Override
            public void onResponse(@NotNull Call<List<BookDto>> call, @NotNull Response<List<BookDto>> response) {
                if(response.isSuccessful()){
                    ObservableList<BookDto> bookList = FXCollections.observableArrayList(response.body());
                    tableViewBooks.setItems(bookList);

                    bookId.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId()).asString());
                    title.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getTitle()).asString());
                    author.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getAuthor()).asString());
                    description.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDescription()).asString());
                    subject.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getAllSubjects()).asString());
                    edition.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getEdition()).asString());
                    pages.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPages()).asString());
                    price.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPrice()).asString());
                    status.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getStatus()).asString());
                    rating.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getRating()).asString());
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<BookDto>> call, @NotNull Throwable throwable) {

            }
        });
    }

    public void addSubject() {
            subjects.add(updateSubjectsList.getValue());
    }

    public void createBook() {
        Integer pg;
        Float bookPrice;

        try {
            // Data is correct, then create the new book
            pg = Integer.parseInt(updatePages.getText());
            bookPrice = Float.parseFloat(updatePrice.getText());
            bookService.insertBook(updateTitle.getText(), updateAuthor.getText(), subjects, updateDescription.getText(), updateEdition.getText(), pg, bookPrice, new Callback<BookDto>() {
                @Override
                public void onResponse(@NotNull Call<BookDto> call, @NotNull Response<BookDto> response) {
                    Platform.runLater(() -> {
                        AlertBox.display("Create book", "Book " + response.body().getTitle() + " succesffully created.");
                        subjects.clear();
                    });
                }

                @Override
                public void onFailure(Call<BookDto> call, Throwable throwable) {
                    Platform.runLater(() -> AlertBox.display("Create book", "Book couldn't be created!"));
                }
            });
        } catch (Exception e) {
            // Invalid data, then display a message
            AlertBox.display("Incorrect data", "You enter invalid data");
        }
    }

    public void deleteBook() {
        BookDto book = tableViewBooks.getSelectionModel().getSelectedItem();
        bookService.deleteBook(book.getId(), new Callback<BookDto>() {
            @Override
            public void onResponse(@NotNull Call<BookDto> call, @NotNull Response<BookDto> response) {
                Platform.runLater(() -> AlertBox.display("Delete book", "Book " + response.body().getTitle() + " succesffully deleted."));
            }

            @Override
            public void onFailure(Call<BookDto> call, Throwable throwable) {
                Platform.runLater(() -> AlertBox.display("Delete book", "Book couldn't be deleted!"));
            }
        });
    }

    public void selectedBook() {
        Platform.runLater(() -> {
            // Get the selected book
            selectedBook = tableViewBooks.getSelectionModel().getSelectedItem();

            // Populate the text fields with the current existing values for the selected book
            updateTitle.setText(selectedBook.getTitle());
            updateAuthor.setText(selectedBook.getAuthor());
            updateDescription.setText(selectedBook.getDescription());
            updateEdition.setText(selectedBook.getEdition());
            updatePages.setText(String.valueOf(selectedBook.getPages()));
            updatePrice.setText(String.valueOf(selectedBook.getPrice()));
            updateStatus.setValue(selectedBook.getStatus());
        });
    }

    public void updateBook() {
        int noOfPages;
        float bookPrice;

        // Prevent from invalid data
        try {
            // Data is correct, then create the new book
            noOfPages = Integer.parseInt(updatePages.getText());
            bookPrice = Float.parseFloat(updatePrice.getText());
            bookService.updateBook(selectedBook.getId(), updateTitle.getText(), updateAuthor.getText(), subjects, updateDescription.getText(),
                    updateEdition.getText(), noOfPages, bookPrice, updateStatus.getValue(), new Callback<BookDto>() {
                        @Override
                        public void onResponse(@NotNull Call<BookDto> call, @NotNull Response<BookDto> response) {
                            Platform.runLater(() -> {
                                AlertBox.display("Update book", "Book " + response.body().getTitle() + " succesffully updated.");
                                // At the end always clear the created subject list
                                subjects.clear();
                            });
                        }

                        @Override
                        public void onFailure(Call<BookDto> call, Throwable throwable) {
                            Platform.runLater(() -> AlertBox.display("Update book", "Book couldn't be updated."));
                        }
                    });
        } catch (Exception e) {
            // Invalid data, then display a message
            AlertBox.display("Incorrect data", "You enter invalid data");
        }
    }

    public void editRatings() {
        JavaFxApplication.changeScene("ratings");
    }
}
