package sungshin.sooon.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sungshin.sooon.domain.entity.Account;
import sungshin.sooon.domain.entity.Post;
import sungshin.sooon.domain.entity.PostLike;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    PostLike findByAccountAndPost(Account account, Post post);
}
