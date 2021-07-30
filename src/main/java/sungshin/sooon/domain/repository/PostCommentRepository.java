package sungshin.sooon.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sungshin.sooon.domain.entity.Account;
import sungshin.sooon.domain.entity.Post;
import sungshin.sooon.domain.entity.PostComment;

import java.util.List;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    List<PostComment> findAllByPost(Post post);

    PostComment findTop1ByAccountAndPostAndIsAnonymous(Account account, Post post, boolean isAnonymous);

    PostComment findTop1ByPostAndIsAnonymousOrderByCreatedAtDesc(Post post, boolean isAnonymous);
    /*
    limit를 주는 메서드 네임은 findTop{num}By 입니다.
    findTop1By: 단 1개만 찾습니다
    https://alalstjr.github.io/java/2019/06/27/JPA-Repository-%EC%BF%BC%EB%A6%AC-limit-%EC%84%A4%EC%A0%95/
     */
}
