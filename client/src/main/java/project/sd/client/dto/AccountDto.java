package project.sd.client.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

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

    Integer id;
    String username;
    String password;
    String email;
    String type;
    Integer penalty;
    boolean accountBlocked;
    Integer borrowsNo;

    List<BookDto> favouriteBooks;
}
