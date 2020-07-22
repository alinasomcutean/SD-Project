package project.sd.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.sd.server.model.Author;

@Repository
public interface AuthorRepo extends JpaRepository<Author, Integer> {

    Author findByName(String name);

    Author findByNameContaining(String authorName);
}
