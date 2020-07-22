package project.sd.server.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import project.sd.server.model.Book;

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
public class BookDto {

    Integer id;

    String title;
    String description;
    String status;
    String edition;
    String author;

    Integer pages;
    Float price;
    Double rating;

    List<SubjectDto> subject;
    List<BorrowDto> borrows;

    public BookDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor().getName();

        this.subject = new ArrayList<>();
        book.getSubject().forEach(b -> subject.add(new SubjectDto(b)));

        this.description = book.getDescription();
        this.status = book.getStatus();
        this.edition = book.getEdition();
        this.pages = book.getPages();
        this.price = book.getPrice();

        this.borrows = new ArrayList<>();
        book.getBorrow().forEach(b -> borrows.add(new BorrowDto(b)));
    }
}
