package sungshin.sooon.domain.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sungshin.sooon.domain.entity.Account;
import sungshin.sooon.domain.entity.Post;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
//한 가지 주의할 점은 @SpringBootTest 대신 @DataJpaTest를 사용하지 말아야 한다는 것이다. 이럴경우 AuditorAware 빈이 정상적으로 등록되지 않아 Auditing 기능 (createdAt)이 제대로 동작하지 않는 것처럼 보일수 있다.
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AccountRepository accountRepository;

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        accountRepository.deleteAll();
    }

    // 레포지토리를 테스트 한 이유는 커스텀(쿼리 직접 작성 등) 메소드가 있기 때문이다. 만약 스프링 data jpa 에서 deleteAll 등 자동완성으로 제공하는 메소드인 경우에는 테스트를 하지 않아도 된다. 왜냐하면 이미 테스트를 모두 거쳤기 떄문이다.
    // 우리가 검증해야 할것은 인터페이스에 추가한 메소드다.
    // 존재할 경우 존재하지 않을 경우 둘다 테스트 해야한다.
    @Test
    void FindAllByAccountOrderByCreatedAt_IfPostExists() {
        //given
        Account account = Account.builder().email("chaeppy@sswu.community").password("tempPassword123").nickname("채피").registeredDateTime(LocalDateTime.now()).build();
        accountRepository.save(account);

        Post post1 = Post.builder().title("포스트 테스트").content("포스트 테스트 입니다").isAnonymous(false).build();
        post1.setAccount(account);
        post1.setCreatedAt(LocalDateTime.now().minusDays(1)); //spring boot Test 아니라서 BaseEntity 가 정상작동하지 않아 직접 설정

        Post post2 = Post.builder().title("포스트 테스트2").content("포스트 테스트2 입니다").isAnonymous(true).build();
        post2.setAccount(account);
        post2.setCreatedAt(LocalDateTime.now());

        postRepository.save(post1);
        postRepository.save(post2);

        //when
        List<Post> posts = postRepository.findAllByAccountOrderByCreatedAtDesc(account);

        //then
        assertThat(posts).containsSequence(post2, post1);
    }

    @Test
    void FindAllByAccountOrderByCreatedAt_IfPostDoesNotExists() {
        //given
        Account account = Account.builder().email("chaeppy@sswu.community").password("tempPassword123").nickname("채피").registeredDateTime(LocalDateTime.now()).build();
        accountRepository.save(account);

        //when
        List<Post> posts = postRepository.findAllByAccountOrderByCreatedAtDesc(account);

        //then
        assertThat(posts).isEmpty();
    }
}