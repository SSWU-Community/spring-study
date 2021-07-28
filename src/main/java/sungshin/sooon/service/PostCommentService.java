package sungshin.sooon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sungshin.sooon.domain.entity.Account;
import sungshin.sooon.domain.entity.Post;
import sungshin.sooon.domain.entity.PostComment;
import sungshin.sooon.domain.repository.PostCommentRepository;
import sungshin.sooon.domain.repository.PostRepository;
import sungshin.sooon.dto.CommentRequestDto;
import sungshin.sooon.dto.CommentResponseDto;
import sungshin.sooon.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostCommentService {
    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAll() {
        return postCommentRepository
                .findAll()
                .stream()
                .map(CommentRequestDto::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CommentResponseDto findById(Long postId, Long commentId) {
        PostComment postComment = findByIdOrThrowNotFoundException(commentId);
        return CommentRequestDto.of(postComment);
    }


    @Transactional
    public CommentResponseDto save(Account account, long postId ,CommentRequestDto commentRequestDto) {
        PostComment postComment = commentRequestDto.toComment();
        postComment.setAccount(account);
        postComment.setPost(account);
        return CommentResponseDto.of(postCommentRepository.save(postComment));
    }

    @Transactional
    public void delete(Account account, Long id) {
        PostComment postComment = findByIdOrThrowNotFoundException(id);

        //현재사용자와 게시글을 작성한 유저의 아이디를 비교해서 권한이있는지 확인해야함. 인터셉터로 해야하나?? 서비스단 말고 더 좋은 위치는 없나?
        if (postComment.getAccount().getId() != account.getId()) {
            throw new AccessDeniedException("삭제 권한이 없습니다.");
        }

        postCommentRepository.delete(postComment);
    }

    private PostComment findByIdOrThrowNotFoundException(Long id){
        PostComment postComment = postCommentRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("댓글이 존재하지 않습니다."));
        return postComment;
    }

    @Transactional
    public CommentResponseDto update(Account account, Long id, CommentRequestDto commentRequestDto) {
        PostComment postComment = findByIdOrThrowNotFoundException(id);

        //현재사용자와 게시글을 작성한 유저의 아이디를 비교해서 권한이있는지 확인해야함. 인터셉터로 해야하나?? 서비스단 말고 더 좋은 위치는 없나?
        if (postComment.getAccount().getId() != account.getId()) {
            throw new AccessDeniedException("수정 권한이 없습니다.");
        }

        CommentRequestDto.apply(postComment);
        return commentResponseDto.of(postComment);
    }
}
