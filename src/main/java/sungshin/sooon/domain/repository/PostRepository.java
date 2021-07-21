package sungshin.sooon.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sungshin.sooon.domain.entity.Account;
import sungshin.sooon.domain.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByAccountOrderByCreatedAt(Account account);
}
