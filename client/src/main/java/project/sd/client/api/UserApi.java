package project.sd.client.api;

import project.sd.client.dto.AccountDto;
import project.sd.client.dto.BorrowDto;
import project.sd.client.dto.PersonDto;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface UserApi {

    @GET("/users")
    Call<List<PersonDto>> getUsers();

    @Multipart
    @POST("/addUser")
    Call<PersonDto> createUser(@Part("firstName") String firstName, @Part("lastName") String lastName, @Part("gender") String gender,
                               @Part("phone") String phone, @Part("username") String username, @Part("password") String password,
                               @Part("email") String email, @Part("accountType") String accountType);

    @DELETE("/deleteUser/{id}")
    Call<PersonDto> deleteUser(@Path("id") Integer id);

    @Multipart
    @POST("/updateUser")
    Call<PersonDto> updateUser(@Part("id") Integer id, @Part("firstName") String firstName, @Part("lastName") String lastName,
                               @Part("gender") String gender, @Part("phone") String phone, @Part("username") String username,
                               @Part("password") String password, @Part("email") String email, @Part("accountType") String accountType);

    @GET("/borrows/{username}")
    Call<List<BorrowDto>> getBorrowsByAccount(@Path("username") String username);

    @Multipart
    @POST("/editSubscriptions")
    Call<AccountDto> editSubscriptions(@Part("username") String username, @Part("subscriptions") String subscriptions);
}
