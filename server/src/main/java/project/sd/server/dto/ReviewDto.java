package project.sd.server.dto;

import lombok.Data;
import lombok.ToString;
import project.sd.server.model.Review;

@Data
@ToString
public class ReviewDto {

    String title;
    String author;
    String reviewText;
    String firstName;
    String lastName;

    public ReviewDto(Review review) {
        this.title = review.getBook().getTitle();
        this.author = review.getBook().getAuthor().getName();
        this.reviewText = review.getTextReview();
        this.firstName = review.getAccount().getPerson().getFirstName();
        this.lastName = review.getAccount().getPerson().getLastName();
    }
}
