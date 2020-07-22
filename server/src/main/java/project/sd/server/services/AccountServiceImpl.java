package project.sd.server.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.sd.server.dto.AccountDto;
import project.sd.server.model.Account;
import project.sd.server.repository.AccountRepo;

import javax.transaction.Transactional;
import java.util.ArrayList;

@RequiredArgsConstructor
@Transactional
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;

    public AccountDto findByUsernameAndPassword(String username, String password) {
        Account account = accountRepo.findByUsernameAndPassword(username, password);
        if (account != null) {
            return new AccountDto(account);
        } else {
            return null;
        }
    }

    public Account insertAccount(String username, String password, String email, String accountType) {
        // Create a new account
        Account account = Account.builder().username(username)
                .password(password)
                .email(email)
                .type(accountType)
                .borrowList(new ArrayList<>())
                .favouriteBooks(new ArrayList<>())
                .penalty(0)
                .accountBlocked(false).build();
        accountRepo.save(account);
        return account;
    }

    public void deleteAccountByPersonId(Integer id) {
        accountRepo.deleteByPersonId(id);
    }

    public Account updateAccount(int accountId, String username, String password, String email, String accountType) {
        // Find the account with the same id
        Account account = accountRepo.findById(accountId).orElseThrow(RuntimeException::new);

        // Change the existing values with the new ones
        account.setUsername(username);
        account.setPassword(password);
        account.setEmail(email);
        account.setType(accountType);
        accountRepo.save(account);

        return account;
    }

    @Override
    public AccountDto editSubscriptions(String username, String subscriptions) {
        Integer subscr = Integer.parseInt(subscriptions);

        //Find the current account
        Account account = accountRepo.findByUsername(username);

        //Save the new number of subscriptions
        account.setBorrowsNo(subscr);
        accountRepo.save(account);
        return new AccountDto(account);
    }
}
