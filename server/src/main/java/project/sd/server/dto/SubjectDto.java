package project.sd.server.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import project.sd.server.model.Subject;

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
public class SubjectDto {

    Integer id;
    String name;
    List<BookDto> books;

    public SubjectDto(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
    }
}
