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

    public String getAllSubjects() {
        String str = "";
        if (!subject.isEmpty()) {
            for (int i = 0; i < subject.size() - 1; i++) {
                str = str + subject.get(i).getName() + ", ";
            }
            str = str + subject.get(subject.size() - 1).getName();
        }
        return str;
    }
}
