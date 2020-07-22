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
@Table(name = "subject")
public class Subject {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(name = "name")
    String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "subject")
    List<Book> books = new ArrayList<>();
}
