package sungshin.sooon.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import sungshin.sooon.domain.post.PostCommentRepository;
import sungshin.sooon.domain.post.PostLikeRepository;
import sungshin.sooon.domain.post.PostRepository;
import sungshin.sooon.dto.PostRequestDto;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostLikeRepository postLikeRepository;

    @Mock
    private PostCommentRepository postCommentRepository;

    @Test
    @WithMockUser
    void create() {

    }

    @Test
    void readAll() {
    }

    @Test
    void readOne() {
    }

    @Test
    void delete() {
    }

    @Test
    void createLike() {
    }

    @Test
    void createComment() {
    }
}