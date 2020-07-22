package project.sd.client.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.web.bind.annotation.RestController;
import project.sd.client.dto.EditRatingsDto;
import project.sd.client.services.RatingService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

@RequiredArgsConstructor
@RestController
@FxmlView("ratings.fxml")
public class RatingsController {

    private final RatingService ratingService;

    // Columns for ratings' table
    @FXML private TableView<EditRatingsDto> tableViewRatings;
    @FXML private TableColumn<EditRatingsDto, String> title;
    @FXML private TableColumn<EditRatingsDto, String> author;
    @FXML private TableColumn<EditRatingsDto, String> username;
    @FXML private TableColumn<EditRatingsDto, String> rating;

    @FXML private TextField newRating;

    public void initialize() {
        ratingService.getRatings(new Callback<List<EditRatingsDto>>() {
            @Override
            public void onResponse(Call<List<EditRatingsDto>> call, Response<List<EditRatingsDto>> response) {
                if(response.isSuccessful()){
                    ObservableList<EditRatingsDto> ratingsList = FXCollections.observableArrayList(response.body());
                    tableViewRatings.setItems(ratingsList);

                    title.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getBookTitle()).asString());
                    author.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getBookAuthor()).asString());
                    username.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getAccountUsername()).asString());
                    rating.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getBookRating()).asString());
                }
            }

            @Override
            public void onFailure(Call<List<EditRatingsDto>> call, Throwable throwable) {

            }
        });
    }

    public void editRating() {
        EditRatingsDto rating = tableViewRatings.getSelectionModel().getSelectedItem();
        Double r = Double.parseDouble(newRating.getText());
        ratingService.editRating(rating.getBookTitle(), rating.getAccountUsername(), r, new Callback<EditRatingsDto>() {
            @Override
            public void onResponse(Call<EditRatingsDto> call, Response<EditRatingsDto> response) {
                initialize();
            }

            @Override
            public void onFailure(Call<EditRatingsDto> call, Throwable throwable) {

            }
        });
    };
}
