package project.sd.client.services;

import project.sd.client.dto.AccountDto;
import project.sd.client.dto.BookDto;
import project.sd.client.dto.BorrowDto;
import retrofit2.Callback;

import java.util.List;

public interface BorrowService {

    void getBorrowsByBookIdAndAccount(String accountUsername, Integer bookId, Callback<List<BorrowDto>> callback);

    void borrowBook(Integer bookId, String accountUsername, Callback<BookDto> callback);

    void getBorrowsByAccount(String username, Callback<List<BorrowDto>> callback);

    void editSubscriptions(String username, String subscriptions, Callback<AccountDto> callback);
}
