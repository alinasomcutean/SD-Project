package project.sd.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.sd.server.model.Review;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Integer> {

    List<Review> findAll();
}
