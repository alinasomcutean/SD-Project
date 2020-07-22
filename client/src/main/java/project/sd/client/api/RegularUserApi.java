package project.sd.client.api;

import project.sd.client.dto.BookDto;
import project.sd.client.dto.BorrowDto;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface RegularUserApi {

    @Multipart
    @POST("/borrows")
    Call<List<BorrowDto>> getBorrowsByBookIdAndAccount(@Part("username") String accountUsername, @Part("bookId") Integer bookId);

    @Multipart
    @POST("/borrowBook")
    Call<BookDto> borrowBook(@Part("bookId") Integer bookId, @Part("accountUsername") String accountUsername);
}
