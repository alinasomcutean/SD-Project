package project.sd.server.dto;

import lombok.Data;
import project.sd.server.model.Rating;

@Data
public class RatingDto {

    Integer id;
    Double rating;
    BookDto bookDto;
    AccountDto accountDto;

    public RatingDto(Rating rating) {
        this.rating = rating.getRating();
        this.bookDto = new BookDto(rating.getBook());
        this.accountDto = new AccountDto(rating.getAccount());
    }
}
