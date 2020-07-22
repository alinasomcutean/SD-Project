package project.sd.client.services;

import project.sd.client.dto.BookDto;
import project.sd.client.dto.EditRatingsDto;
import project.sd.client.dto.RatingDto;
import retrofit2.Callback;

import java.util.List;

public interface RatingService {

    void getRatingByBookAndAccount(String title, String author, Callback<RatingDto> callback);

    void ratingLessThan(Double rating, Callback<List<BookDto>> callback);

    void ratingGreaterThan(Double rating, Callback<List<BookDto>> callback);

    void ratingEquals(Double rating, Callback<List<BookDto>> callback);

    void ratingBetween(Double minRating, Double maxRating, Callback<List<BookDto>> callback);

    void getRatings(Callback<List<EditRatingsDto>> callback);

    void editRating(String title, String username, Double rating, Callback<EditRatingsDto> callback);
}
