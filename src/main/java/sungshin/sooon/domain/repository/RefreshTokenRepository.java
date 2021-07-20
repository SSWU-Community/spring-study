package sungshin.sooon.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sungshin.sooon.domain.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
