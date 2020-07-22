package project.sd.client.api;

import project.sd.client.dto.ReviewDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

import java.util.List;

public interface ReviewApi {

    @Multipart
    @POST("/createReview")
    Call<ReviewDto> createReview(@Part("title") String title, @Part("username") String username, @Part("reviewText") String reviewText);

    @GET("/reviews")
    Call<List<ReviewDto>>getReviews();
}
