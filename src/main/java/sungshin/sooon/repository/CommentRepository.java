package sungshin.sooon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sungshin.sooon.model.PostComment;

public interface CommentRepository extends JpaRepository<PostComment, Long> {

}
