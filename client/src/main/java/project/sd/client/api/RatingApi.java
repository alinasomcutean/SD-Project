package project.sd.client.api;

import project.sd.client.dto.BookDto;
import project.sd.client.dto.EditRatingsDto;
import project.sd.client.dto.RatingDto;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface RatingApi {

    @Multipart
    @POST("/getRating")
    Call<RatingDto> findByBookAndAccount(@Part("title") String title, @Part("username") String username);

    @GET("/ratingLessThan/{rating}")
    Call<List<BookDto>> ratingLessThan(@Path("rating") Double rating);

    @GET("/ratingGreaterThan/{rating}")
    Call<List<BookDto>> ratingGreaterThan(@Path("rating") Double rating);

    @GET("/ratingEquals/{rating}")
    Call<List<BookDto>> ratingEquals(@Path("rating") Double rating);

    @GET("/ratingBetween/{minRating}/{maxRating}")
    Call<List<BookDto>> ratingBetween(@Path("minRating") Double minRating, @Path("maxRating") Double maxRating);

    @GET("/ratings")
    Call<List<EditRatingsDto>> getRatings();

    @Multipart
    @POST("/editRating")
    Call<EditRatingsDto> editRating(@Part("title") String title, @Part("username") String username, @Part("rating") Double rating);
}
