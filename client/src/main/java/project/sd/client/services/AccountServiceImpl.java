package project.sd.client.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import project.sd.client.api.LoginApi;
import project.sd.client.dto.AccountDto;
import project.sd.client.util.SingletonRetrofit;
import project.sd.client.webSocket.WebSocketHandler;
import retrofit2.Callback;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final WebSocketHandler webSocketHandler;
    private LoginApi loginApi = SingletonRetrofit.getInstance().getRetrofit().create(LoginApi.class);

    @Override
    public void login(String username, String password, Callback<AccountDto> callback) {
        loginApi.login(username, password).enqueue(callback);
    }

    @Override
    public void userConnected(String username) {
        try {
            webSocketHandler.getSession().sendMessage(new TextMessage("Connected: " + username));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
