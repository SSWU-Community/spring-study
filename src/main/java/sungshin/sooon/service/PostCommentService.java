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
import sungshin.sooon.dto.CommentRequestDto;
import sungshin.sooon.dto.CommentResponseDto;
import sungshin.sooon.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostCommentService {
    private final PostCommentRepository postCommentRepository;

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAllByPost(Post post) {
        return postCommentRepository
                .findAllByPost(post)
                .stream()
                .map(CommentResponseDto::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CommentResponseDto findById(long id) {
        PostComment postComment = findByIdOrThrowNotFoundException(id);
        return CommentResponseDto.of(postComment);
    }

    @Transactional
    public CommentResponseDto save(Account account, Post post, CommentRequestDto commentRequestDto) {
        //익명이 아닌 경우
        PostComment postComment = commentRequestDto.toComment();
        postComment.setAccount(account);
        postComment.setPost(post);

        //익명인 경우 orderNum 설정
        if (commentRequestDto.isAnonymous()) {
            postComment.setOrderNum(getCurrentPostCommentOrderNum(account, post));
        }

        return CommentResponseDto.of(postCommentRepository.save(postComment));
    }

    /*
    NESTED
    부모 트랜잭션이 존재한다면 중첩 트랜잭션을 생성합니다.
    중첩된 트랜잭션 내부에서 롤백 발생시 해당 중첩 트랜잭션의 시작 지점 까지만 롤백됩니다.
    중첩 트랜잭션은 부모 트랜잭션이 커밋될 때 같이 커밋됩니다.
    부모 트랜잭션이 존재하지 않는다면 새로운 트랜잭션을 생성합니다.
    출처:https://deveric.tistory.com/86
     */
    @Transactional(readOnly = true)
    private long getCurrentPostCommentOrderNum(Account account, Post post) {
        PostComment CommentWithMyOrderNum = postCommentRepository.findTop1ByAccountAndPostAndIsAnonymous(account, post, true);
        if (CommentWithMyOrderNum != null) {
            return CommentWithMyOrderNum.getOrderNum();
        } else {
            return getLastPostCommentOrderNum(post);
        }
    }

    @Transactional(readOnly = true)
    private long getLastPostCommentOrderNum(Post post) {
        PostComment CommentWithLastOrderNum = postCommentRepository.findTop1ByPostAndIsAnonymousOrderByCreatedAtDesc(post, true);
        if (CommentWithLastOrderNum != null) {
            return CommentWithLastOrderNum.getOrderNum() + 1;
        } else {
            return 1;
        }
    }

    @Transactional
    public void delete(Account account, long id) {
        PostComment postComment = findByIdOrThrowNotFoundException(id);

        if (postComment.getAccount().getId() != account.getId()) {
            throw new AccessDeniedException("삭제 권한이 없습니다.");
        }

        postCommentRepository.delete(postComment);
    }

    @Transactional
    public CommentResponseDto update(Account account, Post post, long id, CommentRequestDto commentRequestDto) {
        PostComment postComment = findByIdOrThrowNotFoundException(id);

        if (postComment.getAccount().getId() != account.getId()) {
            throw new AccessDeniedException("수정 권한이 없습니다.");
        }

        //유저가 제일 처음 댓글을 닉네임으로 달고 그 후에 익명으로 수정하여 orderNum이 필요한 경우 고려
        if (commentRequestDto.isAnonymous()) {
            postComment.setOrderNum(getCurrentPostCommentOrderNum(account, post));
        }

        commentRequestDto.apply(postComment);
        return CommentResponseDto.of(postComment);
    }

    private PostComment findByIdOrThrowNotFoundException(long id) {
        PostComment postComment = postCommentRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("댓글이 존재하지 않습니다."));
        return postComment;
    }
}
