package sungshin.sooon.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import sungshin.sooon.domain.account.Account;
import sungshin.sooon.dto.PostResponseDto;

import java.awt.print.Pageable;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
}
