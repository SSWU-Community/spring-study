package sungshin.sooon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sungshin.sooon.model.Post;
import sungshin.sooon.model.PostComment;

import java.util.List;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    List<PostComment> findAllByPost(Post post);
}
