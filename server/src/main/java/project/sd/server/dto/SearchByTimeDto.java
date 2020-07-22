package project.sd.server.dto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class SearchByTimeDto {

    private String time;
    private AccountDto accountDto;
}
