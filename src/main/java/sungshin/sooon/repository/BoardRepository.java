package sungshin.sooon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sungshin.sooon.model.Post;

@Repository
public interface BoardRepository extends JpaRepository<Post, Long> {

}
