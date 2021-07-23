package sungshin.sooon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sungshin.sooon.dto.CommentRequestDto;
import sungshin.sooon.model.Account;
import sungshin.sooon.model.Post;
import sungshin.sooon.model.PostComment;
import sungshin.sooon.repository.AccountRepository;
import sungshin.sooon.repository.BoardRepository;
import sungshin.sooon.repository.CommentRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public long createComment(Long post_id, CommentRequestDto commentDto, String email) {
        Post post = boardRepository.findById(post_id)
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
        return commentRepository.save(saveComment).getPost_comment_id();
    }
}
