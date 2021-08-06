package sungshin.sooon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import sungshin.sooon.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByEmail(@Param("email") String email);

    boolean existsByEmail(String email);
    boolean existsByNickname(String Nickname);
}