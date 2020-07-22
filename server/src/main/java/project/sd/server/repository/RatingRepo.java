package project.sd.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.sd.server.model.Rating;

import java.util.List;

public interface RatingRepo extends JpaRepository<Rating, Integer> {

    Rating findByBookIdAndAccountId(Integer bookId, Integer accountId);

    List<Rating> findByRatingLessThan(Double rating);

    List<Rating> findByRatingGreaterThan(Double rating);

    List<Rating> findByRatingEquals(Double rating);

    List<Rating> findByRatingBetween(Double minRating, Double maxRating);

    List<Rating> findByBookId(Integer bookId);
}
