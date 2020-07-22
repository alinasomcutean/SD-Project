package project.sd.server.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.sd.server.model.Author;
import project.sd.server.model.Book;
import project.sd.server.model.Subject;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {

    @NotNull List<Book> findAll();

    void deleteById(@NotNull Integer id);

    List<Book> findByAuthor(Author author);

    List<Book> findByTitleContaining(String title);

    List<Book> findBySubjectContaining(Subject subject);

    Book findByTitle(String title);

}
