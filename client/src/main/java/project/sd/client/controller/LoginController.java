package project.sd.client.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import project.sd.client.JavaFxApplication;
import project.sd.client.dto.AccountDto;
import project.sd.client.services.AccountService;
import project.sd.client.util.AlertBox;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;

@FxmlView("login.fxml")
@RestController
public class LoginController {

    @Inject
    private AccountService accountService;

    @FXML
    private TextField username;
    @FXML
    private TextField password;

    public void login() {
        accountService.login(username.getText(), password.getText(), new Callback<AccountDto>() {
            @Override
            public void onResponse(@NotNull Call<AccountDto> call, @NotNull Response<AccountDto> response) {
                if(response.isSuccessful()) {
                    AccountDto accountDTO = response.body();
                    Platform.runLater(() -> {
                        accountService.userConnected(accountDTO.getUsername());
                        if (accountDTO.getType().equals("admin")) {
                            JavaFxApplication.changeScene("admin");
                        } else {
                            // Save the current user
                            Authentication authentication = new UsernamePasswordAuthenticationToken(accountDTO, null);
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                            JavaFxApplication.changeScene("regularUser");
                        }
                    });
                }
                else {
                    Platform.runLater(() -> AlertBox.display("Login error", "You must introduce both username and password"));
                }
            }

            @Override
            public void onFailure(@NotNull Call<AccountDto> call, @NotNull Throwable throwable) {
                Platform.runLater(() -> AlertBox.display("Login faild", "It doesn't exist any account with this username"));
            }
        });
    }
}
