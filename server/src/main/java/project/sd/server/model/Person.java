package project.sd.server.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;

import static lombok.AccessLevel.PRIVATE;

@EnableAutoConfiguration
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
@Builder
@ToString
@Setter
@Getter
@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(name = "lastName")
    String lastName;

    @Column(name = "firstName")
    String firstName;

    @Column(name = "gender")
    String gender;

    @Column(name = "phone")
    String phone;

    @OneToOne(cascade = CascadeType.REMOVE)
    Account account;
}
