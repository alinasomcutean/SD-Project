package project.sd.server.dto;

import lombok.Data;
import lombok.ToString;
import project.sd.server.model.Rating;

@Data
@ToString
public class EditRatingsDto {

    String bookTitle;
    String bookAuthor;
    String accountUsername;

    Double bookRating;

    public EditRatingsDto(Rating rating) {
        this.bookTitle = rating.getBook().getTitle();
        this.bookAuthor = rating.getBook().getAuthor().getName();
        this.accountUsername = rating.getAccount().getUsername();
        this.bookRating = rating.getRating();
    }
}
