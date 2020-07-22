package project.sd.server.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import project.sd.server.model.Borrow;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor             // Creates constructor with all of the fields as arguments
@NoArgsConstructor              // Creates constructor with no arguments (Default)
@FieldDefaults(level = PRIVATE) // Sets the visibility of all fields to PRIVATE
@Builder                        // Builder Pattern (Lab 2)
@ToString                       // ToString method implementation for class
@Getter                         // Getters for all fields of the class
@Setter                         // Setters for all fields of the class
@Data
public class BorrowDto {

    Integer id;

    String dueDate;
    String borrowedDate;
    String returnState;
    String title;
    String author;

    public BorrowDto(Borrow borrow) {
        this.id = borrow.getId();
        this.dueDate = borrow.getDueDate();
        this.borrowedDate = borrow.getBorrowedDate();
        this.returnState = borrow.getReturnState();
        this.title = borrow.getBook().getTitle();
        this.author = borrow.getBook().getAuthor().getName();
    }
}
