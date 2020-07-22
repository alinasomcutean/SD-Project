package project.sd.client.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.web.bind.annotation.RestController;
import project.sd.client.dto.ReviewDto;
import project.sd.client.services.ReviewService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

@RequiredArgsConstructor
@RestController
@FxmlView("reviews.fxml")
public class ReviewsController {

    private final ReviewService reviewService;

    @FXML private TableView<ReviewDto> tableViewReviews;
    @FXML private TableColumn<ReviewDto, String> title;
    @FXML private TableColumn<ReviewDto, String> author;
    @FXML private TableColumn<ReviewDto, String> reviewText;
    @FXML private TableColumn<ReviewDto, String> firstName;
    @FXML private TableColumn<ReviewDto, String> lastName;

    public void initialize() {
        reviewService.getReviews(new Callback<List<ReviewDto>>() {
            @Override
            public void onResponse(Call<List<ReviewDto>> call, Response<List<ReviewDto>> response) {
                ObservableList<ReviewDto> reviewList = FXCollections.observableArrayList(response.body());
                tableViewReviews.setItems(reviewList);

                title.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getTitle()).asString());
                author.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getAuthor()).asString());
                reviewText.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getReviewText()).asString());
                firstName.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getFirstName()).asString());
                lastName.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getLastName()).asString());
            }

            @Override
            public void onFailure(Call<List<ReviewDto>> call, Throwable throwable) {

            }
        });
    }
}
