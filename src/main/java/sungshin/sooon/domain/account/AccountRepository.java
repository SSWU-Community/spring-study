package sungshin.sooon.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;
import sungshin.sooon.domain.account.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findAccountByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
}
