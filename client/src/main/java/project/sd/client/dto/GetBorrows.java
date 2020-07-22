package project.sd.client.dto;

public class GetBorrows {

    private AccountDto accountDto;
    private Integer id;

    public GetBorrows(AccountDto accountDto, Integer id) {
        this.accountDto = accountDto;
        this.id = id;
    }
}
