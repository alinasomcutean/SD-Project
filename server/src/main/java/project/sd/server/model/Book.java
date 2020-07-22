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
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(name = "title")
    String title;

    @OneToOne
    Author author;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable
    List<Subject> subject = new ArrayList<>();

    @Column(name = "description")
    String description;

    @Column(name = "status")
    String status;

    @Column(name = "edition")
    String edition;

    @Column(name = "pages")
    Integer pages;

    @Column(name = "price")
    Float price;

    @OneToMany(mappedBy = "book")
    List<Borrow> borrow = new ArrayList<>();

    @OneToMany
    List<Account> waitingList = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    List<Review> reviews = new ArrayList<>();
}
