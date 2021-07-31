package sungshin.sooon.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface PostImagesRepository extends JpaRepository<PostImages, Long> {
}
