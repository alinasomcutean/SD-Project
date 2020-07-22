package project.sd.client.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ReviewDto {

    String title;
    String author;
    String reviewText;
    String firstName;
    String lastName;
}
