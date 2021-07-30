package sungshin.sooon.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;
import sungshin.sooon.domain.account.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
