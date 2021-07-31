package sungshin.sooon.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sungshin.sooon.domain.account.Account;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByAccountAndPost(Account account, Post post);
}
