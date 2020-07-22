package project.sd.server.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.sd.server.model.Subject;

import java.util.List;

@Repository
public interface SubjectRepo extends JpaRepository<Subject, Integer> {

    @NotNull List<Subject> findAll();

    Subject findByName(String name);

    Subject findByNameContaining(String name);
}
