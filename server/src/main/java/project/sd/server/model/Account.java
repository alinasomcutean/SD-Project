package project.sd.server.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    Integer id;

    @Column(name = "username")
    String username;

    @Column(name = "password")
    String password;

    @Column(name = "email")
    String email;

    @Column(name = "account_type")
    String type;

    @Column(name = "penalty")
    Integer penalty;

    @Column(name = "account_blocked")
    boolean accountBlocked;

    @Column(name = "borrows_no")
    Integer borrowsNo;

    @OneToMany
    List<Borrow> borrowList = new ArrayList<>();

    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "account")
    Person person;

    @OneToMany
    List<Book> favouriteBooks = new ArrayList<>();
}
