package project.sd.client.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class EditRatingsDto {

    String bookTitle;
    String bookAuthor;
    String accountUsername;

    Double bookRating;
}
