package sungshin.sooon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sungshin.sooon.model.Account;
import sungshin.sooon.model.Post;
import sungshin.sooon.model.PostLike;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByPostAndUser(Account account, Post post);
}
