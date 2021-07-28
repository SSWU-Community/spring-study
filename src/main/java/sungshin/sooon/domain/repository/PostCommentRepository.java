package sungshin.sooon.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sungshin.sooon.domain.entity.PostComment;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
}
