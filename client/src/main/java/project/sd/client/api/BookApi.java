package project.sd.client.api;

import project.sd.client.dto.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface BookApi {

    @GET("/books")
    Call<List<BookDto>> getBooks();

    @GET("/genres")
    Call<List<SubjectDto>> getSubjects();

    @Multipart
    @POST("/addBook")
    Call<BookDto> createBook(@Part("title") String title, @Part("author") String author, @Part("subjectList") List<String> subjectList,
                             @Part("description") String description, @Part("edition") String edition,
                             @Part("pages") Integer pages, @Part("price") Float price);

    @DELETE("/deleteBook/{id}")
    Call<BookDto> deleteBook(@Path("id") Integer id);

    @Multipart
    @POST("/updateBook")
    Call<BookDto> updateBook(@Part("id") Integer id, @Part("title") String title, @Part("author") String author, @Part("subjectList") List<String> subjectList,
                             @Part("description") String description, @Part("edition") String edition, @Part("pages") Integer pages, @Part("price") Float price,
                             @Part("status") String status);

    @GET("/searchByAuthor/{authorName}")
    Call<List<BookDto>> searchByAuthor(@Path("authorName") String authorName);

    @GET("/searchByTitle/{title}")
    Call<List<BookDto>> searchByTitle(@Path("title") String title);

    @GET("/searchBySubject/{subject}")
    Call<List<BookDto>> searchBySubject(@Path("subject") String subject);

    @Multipart
    @POST("/searchByTime")
    Call<List<BookDto>> searchByTime(@Part("username") String username, @Part("time") String time);

    @Multipart
    @POST("/favouriteBooks")
    Call<List<BookDto>> getFavouriteBooks(@Part("username") String username);

    @Multipart
    @POST("/addToFavourites")
    Call<BookDto> addToFavourites(@Part("title") String title, @Part("username") String username);

    @Multipart
    @POST("/removeFromFavourites")
    Call<BookDto> removeFromFavourites(@Part("title") String title, @Part("username") String username);

    @Multipart
    @POST("/returnBook")
    Call<AccountDto> returnBook(@Part("title") String title, @Part("username") String username, @Part("returnState") String returnState);

    @Multipart
    @POST("/putInQueue")
    Call<AccountDto> putInQueue(@Part("title") String title, @Part("username") String username);

    @Multipart
    @POST("/rating")
    Call<RatingDto> rateBook(@Part("title") String title, @Part("username") String username, @Part("rating") Double rating);
}
