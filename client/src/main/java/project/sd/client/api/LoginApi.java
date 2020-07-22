package project.sd.client.api;

import project.sd.client.dto.AccountDto;
import retrofit2.Call;
import retrofit2.http.*;

public interface LoginApi {

    @Multipart
    @POST(value = "login")
    Call<AccountDto> login(@Part("username") String username, @Part("password") String password);
}
