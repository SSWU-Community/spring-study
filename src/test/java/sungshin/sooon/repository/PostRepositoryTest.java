package sungshin.sooon.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import sungshin.sooon.domain.post.PostRepository;
import sungshin.sooon.dto.RegisterRequestDto;
import sungshin.sooon.domain.account.Account;
import sungshin.sooon.domain.post.Post;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class PostRepositoryTest {
    @Autowired
    PostRepository postRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @AfterEach
    public void cleanup(){
        postRepository.deleteAll();
    }

    @Test
    public void 조회(){
        List<Post> postList = postRepository.findAll();
        System.out.println("size :" + postList.size());
    }

    //account_id null
    @Test
    public void 저장_조회(){
        String title = "수정광산";
        String content = "에 오신걸 환영합니다!";

        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .email("test@sungshin.ac.kr")
                .nickname("test11")
                .password("sujeongmining2021!")
                .build();
        Account account1 = registerRequestDto.toAccount(passwordEncoder);

        postRepository.save(Post.builder()
                            .title(title)
                            .content(content)
                            .account(account1)
                            .is_anonymous(true)
                            .created_at(LocalDateTime.now())
                            .build());

        List<Post> postList = postRepository.findAll();
        Post post = postList.get(0);
        System.out.println(post.getTitle() + post.getContent());
    }
}
