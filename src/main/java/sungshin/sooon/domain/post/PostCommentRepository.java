package sungshin.sooon.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import sungshin.sooon.domain.post.PostComment;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
}
