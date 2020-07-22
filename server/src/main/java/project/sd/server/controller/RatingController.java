package project.sd.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.sd.server.dto.BookDto;
import project.sd.server.dto.EditRatingsDto;
import project.sd.server.dto.RatingDto;
import project.sd.server.services.RatingService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/getRating")
    public RatingDto findByBookAndAccount(String title, String username) {
        return ratingService.findByBookIdAndAccountId(title, username);
    }

    @GetMapping("/ratingLessThan/{rating}")
    public List<BookDto> ratingLessThan(@PathVariable Double rating) {
        return ratingService.findByRatingLessThan(rating);
    }

    @GetMapping("/ratingGreaterThan/{rating}")
    public List<BookDto> ratingGreaterThan(@PathVariable Double rating) {
        return ratingService.findByRatingGreaterThan(rating);
    }

    @GetMapping("/ratingEquals/{rating}")
    public List<BookDto> ratingEquals(@PathVariable Double rating) {
        return ratingService.findByRatingEquals(rating);
    }

    @GetMapping("/ratingBetween/{minRating}/{maxRating}")
    public List<BookDto> ratingBetween(@PathVariable Double minRating, @PathVariable Double maxRating) {
        return ratingService.findByRatingBetween(minRating, maxRating);
    }

    @GetMapping("/ratings")
    public List<EditRatingsDto> getRatings() {
        return ratingService.findAll();
    }

    @PostMapping("/editRating")
    public EditRatingsDto editRating(@RequestParam String title, @RequestParam String username, @RequestParam Double rating) {
        return ratingService.editRating(title, username, rating);
    }
}
