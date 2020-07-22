package project.sd.client.services;

import org.springframework.stereotype.Service;
import project.sd.client.api.ReviewApi;
import project.sd.client.dto.ReviewDto;
import project.sd.client.util.SingletonRetrofit;
import retrofit2.Callback;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewApi reviewApi = SingletonRetrofit.getInstance().getRetrofit().create(ReviewApi.class);

    @Override
    public void createReview(String title, String username, String reviewTest, Callback<ReviewDto> callback) {
        reviewApi.createReview(title, username, reviewTest).enqueue(callback);
    }

    @Override
    public void getReviews(Callback<List<ReviewDto>> callback) {
        reviewApi.getReviews().enqueue(callback);
    }
}
