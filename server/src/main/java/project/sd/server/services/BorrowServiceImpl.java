package project.sd.server.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.sd.server.dto.BorrowDto;
import project.sd.server.model.Account;
import project.sd.server.model.Borrow;
import project.sd.server.repository.AccountRepo;
import project.sd.server.repository.BorrowRepo;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class BorrowServiceImpl implements BorrowService{

    private final BorrowRepo borrowRepo;
    private final AccountRepo accountRepo;

    public void deleteBorrowByBookId(Integer id) {
        List<Borrow> borrows = borrowRepo.findByBookId(id);

        List<Account> accounts = accountRepo.findAll();
        for(Account a : accounts) {
            List<Borrow> accountBorrows = a.getBorrowList();
            accountBorrows.removeIf(b -> b.getBook().getId().equals(id));
        }

        if(!borrows.isEmpty()){
            borrows.forEach(borrowRepo::delete);
        }
    }

    public List<BorrowDto> viewBorrowsByBookIdAndAccount(String username, Integer id) {
        // Find the list of borrows of a given account
        Account account = accountRepo.findByUsername(username);
        List<Borrow> borrows = account.getBorrowList();

        // Create a new list of borrows with borrows of the given account and which are part of a specific book
        List<BorrowDto> borrowDTOS = new ArrayList<>();

        for (Borrow b : borrows) {
            if (b.getBook().getId().equals(id)) {
                borrowDTOS.add(new BorrowDto(b));
            }
        }

        return borrowDTOS;
    }

    public List<BorrowDto> viewBorrowsByAccount(String username) {
        // Find the list of all borrows of a given account
        Account account = accountRepo.findByUsername(username);
        List<Borrow> borrows = account.getBorrowList();

        List<BorrowDto> borrowDTOS = new ArrayList<>();

        for (Borrow b : borrows) {
            borrowDTOS.add(new BorrowDto(b));
        }

        return borrowDTOS;
    }
}
