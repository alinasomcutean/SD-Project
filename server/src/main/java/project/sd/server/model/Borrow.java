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
@Table(name = "borrow")
public class Borrow {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(name = "dueDate")
    String dueDate;

    @Column(name = "borrowedDate")
    String borrowedDate;

    @Column(name = "return_state")
    String returnState;

    @ManyToOne
    Book book;
}
