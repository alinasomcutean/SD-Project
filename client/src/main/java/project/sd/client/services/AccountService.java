package project.sd.client.services;

import project.sd.client.dto.AccountDto;
import retrofit2.Callback;


public interface AccountService {

     void login(String username, String password, Callback<AccountDto> callback);

     void userConnected(String username);
}
