package project.sd.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.sd.server.model.Account;

@Repository
public interface AccountRepo extends JpaRepository<Account, Integer> {

    Account findByUsernameAndPassword(String username, String password);

    Account findByUsername(String username);

    void deleteByPersonId(Integer id);
}
