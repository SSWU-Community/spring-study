package sungshin.sooon.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import sungshin.sooon.domain.post.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
