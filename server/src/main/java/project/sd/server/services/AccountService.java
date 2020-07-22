package project.sd.server.services;


import project.sd.server.dto.AccountDto;
import project.sd.server.model.Account;

public interface AccountService {

    AccountDto findByUsernameAndPassword(String username, String password);

    Account insertAccount(String username, String password, String email, String accountType);

    void deleteAccountByPersonId(Integer id);

    Account updateAccount(int accountId, String username, String password, String email, String accountType);

    AccountDto editSubscriptions(String username, String subscriptions);
}
