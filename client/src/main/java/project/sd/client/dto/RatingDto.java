package project.sd.client.dto;

import lombok.Data;

@Data
public class RatingDto {

    Integer id;
    Double rating;
    BookDto bookDto;
    AccountDto accountDto;
}
