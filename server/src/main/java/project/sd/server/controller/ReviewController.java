package project.sd.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.sd.server.dto.ReviewDto;
import project.sd.server.services.ReviewService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/createReview")
    public ReviewDto createReview(@RequestParam String title, @RequestParam String username, @RequestParam String reviewText) {
        return reviewService.createReview(title, username, reviewText);
    }

    @GetMapping("/reviews")
    public List<ReviewDto> getReviews() {
        return reviewService.getReviews();
    }
}
