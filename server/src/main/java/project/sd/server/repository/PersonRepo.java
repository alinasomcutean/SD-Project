package project.sd.server.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.sd.server.model.Person;

import java.util.List;

@Repository
public interface PersonRepo extends JpaRepository<Person, Integer> {

    @NotNull List<Person> findAll();

    void deleteById(@NotNull Integer id);
}
