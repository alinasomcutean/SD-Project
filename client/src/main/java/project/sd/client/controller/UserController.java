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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import project.sd.client.JavaFxApplication;
import project.sd.client.dto.PersonDto;
import project.sd.client.services.AccountService;
import project.sd.client.services.PersonService;
import project.sd.client.util.AlertBox;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;
import java.util.List;

@RestController
@FxmlView("users.fxml")
public class UserController {

    @Inject
    private PersonService personService;
    @Inject
    private AccountService accountService;

    // Table columns
    @FXML
    private TableView<PersonDto> tableViewUsers;
    @FXML private javafx.scene.control.TableColumn<PersonDto, String> personId;
    @FXML private javafx.scene.control.TableColumn<PersonDto, String> firstName;
    @FXML private javafx.scene.control.TableColumn<PersonDto, String> lastName;
    @FXML private javafx.scene.control.TableColumn<PersonDto, String> gender;
    @FXML private javafx.scene.control.TableColumn<PersonDto, String> phone;
    @FXML private javafx.scene.control.TableColumn<PersonDto, String> email;
    @FXML private javafx.scene.control.TableColumn<PersonDto, String> username;
    @FXML private javafx.scene.control.TableColumn<PersonDto, String> password;
    @FXML private TableColumn<PersonDto, String> type;

    @FXML private TextField newFirstName;
    @FXML private TextField newLastName;
    @FXML private TextField newPhone;
    @FXML private TextField newEmail;
    @FXML private TextField newUsername;
    @FXML private TextField newPassword;

    @FXML public ComboBox<String> selectGender;
    @FXML public ComboBox<String> selectAccountType;

    private PersonDto selectedPerson;

    public void viewUsers() {
        personService.getUsers(new Callback<List<PersonDto>>() {
            @Override
            public void onResponse(Call<List<PersonDto>> call, Response<List<PersonDto>> response) {
                if (response.isSuccessful()) {
                    ObservableList<PersonDto> personList = FXCollections.observableArrayList(response.body());
                    tableViewUsers.setItems(personList);

                    personId.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId()).asString());
                    firstName.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getFirstName()).asString());
                    lastName.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getLastName()).asString());
                    gender.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getGender()).asString());
                    phone.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPhone()).asString());
                    email.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getAccount().getEmail()).asString());
                    username.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getAccount().getUsername()).asString());
                    password.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getAccount().getPassword()).asString());
                    type.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getAccount().getType()).asString());
                }
            }

            @Override
            public void onFailure(Call<List<PersonDto>> call, Throwable throwable) {

            }
        });
    }

    public void createUser() {
        if(!newEmail.getText().contains("@")) {
            AlertBox.display("Invalid data", "Incorrect format type for email!");
        } else {
            personService.createUser(newFirstName.getText(), newLastName.getText(), selectGender.getValue(), newPhone.getText(),
                    newUsername.getText(), newPassword.getText(), newEmail.getText(), selectAccountType.getValue(), new Callback<PersonDto>() {
                @Override
                public void onResponse(Call<PersonDto> call, Response<PersonDto> response) {
                    Platform.runLater(() -> AlertBox.display("Create user", "User " + response.body().getFirstName() + " " + response.body().getLastName() + " succesffully created."));
                }

                @Override
                public void onFailure(Call<PersonDto> call, Throwable throwable) {
                    Platform.runLater(() -> AlertBox.display("Create user", "User couldn't be created!"));
                }
            });
        }
    }

    public void deleteUser() {
        PersonDto person = tableViewUsers.getSelectionModel().getSelectedItem();
        personService.deleteUser(person.getId(), new Callback<PersonDto>() {
            @Override
            public void onResponse(Call<PersonDto> call, Response<PersonDto> response) {
                Platform.runLater(() -> AlertBox.display("Delete user", "User " + response.body().getFirstName() + " " + response.body().getLastName() + " succesffully deleted."));
            }

            @Override
            public void onFailure(Call<PersonDto> call, Throwable throwable) {
                Platform.runLater(() -> AlertBox.display("Delete user", "User couldn't be deleted!"));
            }
        });
    }

    public void selectUser() {
        Platform.runLater(() -> {
            selectedPerson = tableViewUsers.getSelectionModel().getSelectedItem();
            newFirstName.setText(selectedPerson.getFirstName());
            newLastName.setText(selectedPerson.getLastName());
            newPhone.setText(selectedPerson.getPhone());
            newEmail.setText(selectedPerson.getAccount().getEmail());
            newUsername.setText(selectedPerson.getAccount().getUsername());
            newPassword.setText(selectedPerson.getAccount().getPassword());
            selectGender.setValue(selectedPerson.getGender());
            selectAccountType.setValue(selectedPerson.getAccount().getType());
        });
    }

    public void updateUser() {
        if(!newEmail.getText().contains("@")) {
            AlertBox.display("Invalid data", "Incorrect format type for email!");
        } else {
            personService.updateUser(selectedPerson.getId(), newFirstName.getText(), newLastName.getText(), selectGender.getValue(), newPhone.getText(),
                    newUsername.getText(), newPassword.getText(), newEmail.getText(), selectAccountType.getValue(), new Callback<PersonDto>() {
                        @Override
                        public void onResponse(Call<PersonDto> call, Response<PersonDto> response) {
                            Platform.runLater(() -> AlertBox.display("Update user", "User " + response.body().getFirstName() + " " + response.body().getLastName() + " succesffully updated."));
                        }

                        @Override
                        public void onFailure(Call<PersonDto> call, Throwable throwable) {
                            Platform.runLater(() -> AlertBox.display("Update user", "User couldn't be updated!"));
                        }
                    });
        }
    }

    public void editBorrows() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(tableViewUsers.getSelectionModel().getSelectedItem(), null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        JavaFxApplication.changeScene("borrows");
    }
}
