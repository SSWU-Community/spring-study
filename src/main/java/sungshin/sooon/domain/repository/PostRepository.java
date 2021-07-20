package sungshin.sooon.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sungshin.sooon.domain.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
