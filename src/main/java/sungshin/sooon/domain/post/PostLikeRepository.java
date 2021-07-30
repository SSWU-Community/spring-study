package sungshin.sooon.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import sungshin.sooon.domain.account.Account;
import sungshin.sooon.domain.post.Post;
import sungshin.sooon.domain.post.PostLike;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    PostLike findByAccountAndPost(Account account, Post post);
}
