package project.sd.server.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.sd.server.model.Borrow;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowRepo extends JpaRepository<Borrow, Integer> {

    @NotNull List<Borrow> findAll();

    List<Borrow> findByBookId(Integer id);

    void deleteByBookId(Integer id);

    Optional<Borrow> findById(Integer id);
}
