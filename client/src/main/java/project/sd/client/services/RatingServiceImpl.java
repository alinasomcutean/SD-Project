package project.sd.client.services;

import org.springframework.stereotype.Service;
import project.sd.client.api.RatingApi;
import project.sd.client.dto.BookDto;
import project.sd.client.dto.EditRatingsDto;
import project.sd.client.dto.RatingDto;
import project.sd.client.util.SingletonRetrofit;
import retrofit2.Callback;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService{

    private RatingApi ratingApi = SingletonRetrofit.getInstance().getRetrofit().create(RatingApi.class);

    @Override
    public void getRatingByBookAndAccount(String title, String author, Callback<RatingDto> callback) {
        ratingApi.findByBookAndAccount(title, author).enqueue(callback);
    }

    @Override
    public void ratingLessThan(Double rating, Callback<List<BookDto>> callback) {
        ratingApi.ratingLessThan(rating).enqueue(callback);
    }

    @Override
    public void ratingGreaterThan(Double rating, Callback<List<BookDto>> callback) {
        ratingApi.ratingGreaterThan(rating).enqueue(callback);
    }

    @Override
    public void ratingEquals(Double rating, Callback<List<BookDto>> callback) {
        ratingApi.ratingEquals(rating).enqueue(callback);
    }

    @Override
    public void ratingBetween(Double minRating, Double maxRating, Callback<List<BookDto>> callback) {
        ratingApi.ratingBetween(minRating, maxRating).enqueue(callback);
    }

    @Override
    public void getRatings(Callback<List<EditRatingsDto>> callback) {
        ratingApi.getRatings().enqueue(callback);
    }

    @Override
    public void editRating(String title, String username, Double rating, Callback<EditRatingsDto> callback) {
        ratingApi.editRating(title, username, rating).enqueue(callback);
    }
}
