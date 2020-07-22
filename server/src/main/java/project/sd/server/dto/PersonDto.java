package project.sd.server.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import project.sd.server.model.Person;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor             // Creates constructor with all of the fields as arguments
@NoArgsConstructor              // Creates constructor with no arguments (Default)
@FieldDefaults(level = PRIVATE) // Sets the visibility of all fields to PRIVATE
@Builder                        // Builder Pattern (Lab 2)
@ToString                       // ToString method implementation for class
@Getter                         // Getters for all fields of the class
@Setter                         // Setters for all fields of the class
@Data
public class PersonDto {

    Integer id;

    String lastName;
    String firstName;
    String gender;
    String phone;

    AccountDto account;

    public PersonDto(Person person) {
        this.id = person.getId();
        this.lastName = person.getLastName();
        this.firstName = person.getFirstName();
        this.gender = person.getGender();
        this.phone = person.getPhone();
        this.account = new AccountDto(person.getAccount());
    }
}
