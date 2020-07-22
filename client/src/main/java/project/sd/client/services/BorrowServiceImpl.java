package project.sd.client.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.sd.client.api.RegularUserApi;
import project.sd.client.api.UserApi;
import project.sd.client.dto.AccountDto;
import project.sd.client.dto.BookDto;
import project.sd.client.dto.BorrowDto;
import project.sd.client.util.SingletonRetrofit;
import project.sd.client.webSocket.WebSocketHandler;
import retrofit2.Callback;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BorrowServiceImpl implements BorrowService {

    private RegularUserApi regularUserApi = SingletonRetrofit.getInstance().getRetrofit().create(RegularUserApi.class);
    private UserApi userApi = SingletonRetrofit.getInstance().getRetrofit().create(UserApi.class);
    private final WebSocketHandler webSocketHandler;

    @Override
    public void getBorrowsByBookIdAndAccount(String username, Integer bookId, Callback<List<BorrowDto>> callback) {
        regularUserApi.getBorrowsByBookIdAndAccount(username, bookId).enqueue(callback);
    }

    @Override
    public void borrowBook(Integer bookId, String accountUsername, Callback<BookDto> callback) {
        regularUserApi.borrowBook(bookId, accountUsername).enqueue(callback);
    }

    @Override
    public void getBorrowsByAccount(String username, Callback<List<BorrowDto>> callback) {
        userApi.getBorrowsByAccount(username).enqueue(callback);
    }

    @Override
    public void editSubscriptions(String username, String subscriptions, Callback<AccountDto> callback) {
        userApi.editSubscriptions(username, subscriptions).enqueue(callback);
    }
}
