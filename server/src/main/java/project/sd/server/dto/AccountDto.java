package project.sd.server.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import project.sd.server.model.Account;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor             // Creates constructor with all of the fields as arguments
@NoArgsConstructor              // Creates constructor with no arguments (Default)
@FieldDefaults(level = PRIVATE) // Sets the visibility of all fields to PRIVATE
@Builder                        // Builder Pattern (Lab 2)
@ToString                       // ToString method implementation for class
@Getter                         // Getters for all fields of the class
@Setter                         // Setters for all fields of the class
@Data
public class AccountDto {

    String username;
    String password;
    String email;
    String type;
    Integer penalty;
    boolean accountBlocked;
    Integer borrowsNo;

    List<BorrowDto> borrows;
    List<BookDto> favouriteBooks;

    public AccountDto(Account account) {
        this.username = account.getUsername();
        this.password = account.getPassword();
        this.email = account.getEmail();
        this.type = account.getType();
        this.penalty = account.getPenalty();
        this.accountBlocked = account.isAccountBlocked();
        this.borrowsNo = account.getBorrowsNo();

        this.borrows = new ArrayList<>();
        account.getBorrowList().forEach(b -> borrows.add(new BorrowDto(b)));

        this.favouriteBooks = new ArrayList<>();
        account.getFavouriteBooks().forEach(b -> favouriteBooks.add(new BookDto(b)));
    }
}
