package sungshin.sooon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sungshin.sooon.dto.PostCommentRequestDto;
import sungshin.sooon.model.Account;
import sungshin.sooon.model.Post;
import sungshin.sooon.model.PostComment;
import sungshin.sooon.repository.AccountRepository;
import sungshin.sooon.repository.PostRepository;
import sungshin.sooon.repository.PostCommentRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final PostCommentRepository postCommentRepository;
    private final PostRepository postRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public long createComment(Long post_id, PostCommentRequestDto commentDto, String email) {
        Post post = postRepository.findById(post_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));
        Account account = accountRepository.findByEmail(email);
        if(account == null) {
            throw new UsernameNotFoundException(email);
        }

        PostComment comment = PostComment.builder()
                .comment(commentDto.getComment())
                .created_at(LocalDateTime.now())
                .build();

        comment.mappingBoardAndAccount(post, account);

        PostComment saveComment = commentDto.toComment();
        return postCommentRepository.save(saveComment).getId();
    }
}
