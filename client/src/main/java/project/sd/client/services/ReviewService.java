package project.sd.client.services;

import project.sd.client.dto.ReviewDto;
import retrofit2.Callback;

import java.util.List;

public interface ReviewService {

    void createReview(String title, String username, String reviewTest, Callback<ReviewDto> callback);

    void getReviews(Callback<List<ReviewDto>> callback);
}
