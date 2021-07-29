package sungshin.sooon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sungshin.sooon.dto.PostCommentRequestDto;
import sungshin.sooon.dto.PostCommentResponseDto;
import sungshin.sooon.dto.PostRequestDto;
import sungshin.sooon.dto.PostResponseDto;
import sungshin.sooon.exception.AlreadyExistsException;
import sungshin.sooon.exception.PostNotFound;
import sungshin.sooon.model.Account;
import sungshin.sooon.model.Post;
import sungshin.sooon.model.PostComment;
import sungshin.sooon.model.PostLike;
import sungshin.sooon.repository.PostCommentRepository;
import sungshin.sooon.repository.PostLikeRepository;
import sungshin.sooon.repository.PostRepository;

import javax.transaction.Transactional;
import java.nio.file.AccessDeniedException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostCommentRepository postCommentRepository;

    private Post getPostInService(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFound("해당 게시물이 존재하지 않습니다."));
        return post;
    }

    private Account getUserInService(Account user) {
        Account account = accountRepository.findById(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("게시글 작성 권한이 없습니다."));
        return account;
    }

    // 게시글 전체 리스트


    // CREATE
    @Transactional
    public PostResponseDto save(Account account, PostRequestDto postRequestDto) {
        Post post = postRequestDto.toPost();
        post.setAccount(account);
        return PostResponseDto.from(postRepository.save(post));
    }

    // READ
    @Transactional
    public PostResponseDto read(Long postId) {
        return PostResponseDto.from(getPostInService(postId));
    }

    // UPDATE
    @Transactional
    public PostResponseDto update(Account account, Long postId, PostRequestDto postRequestDto) throws AccessDeniedException {
        Post post = getPostInService(postId);
        if (post.getAccount().getId().equals(account.getId())) {
            throw new AccessDeniedException("게시글의 수정 권한이 없습니다.");
        }
        postRequestDto.apply(post);
        return PostResponseDto.from(post);
    }

    // DELETE
    @Transactional
    public void delete(Account account, Long postId) throws AccessDeniedException {
        Post post = getPostInService(postId);
        if (post.getAccount().getId().equals(account.getId())) {
            throw new AccessDeniedException("게시글의 삭제 권한이 없습니다.");
        }
        postRepository.delete(post);

    }

    // 좋아요
    @Transactional
    public void like(Account user, Long postId) {
        Post post = getPostInService(postId);

        Optional<PostLike> byPostAndUser = postLikeRepository.findByPostAndUser(user, post);

        byPostAndUser.ifPresentOrElse(
                postLike -> {
                    postLikeRepository.delete(postLike);
                    post.discountLike(postLike);
                },
                () -> {
                    PostLike postLike = new PostLike();
                    postLike.setAccount(user);
                    postLike.setPost(post);
                    postLikeRepository.save(postLike);
                }
        );
    }

    // 댓글 생성
    @Transactional
    public PostCommentResponseDto savePostComment(Account account, Long postId, PostCommentRequestDto postCommentRequestDto) {
        Post post = getPostInService(postId);
        PostComment comment = postCommentRequestDto.toComment();
        comment.setAccount(account);
        comment.setPost(post);
        return PostCommentResponseDto.from(postCommentRepository.save(comment));
    }

    // 댓글 삭제
    @Transactional
    public void deletePostComment(Account account, Long postCommentId) throws AccessDeniedException {
        PostComment comment = postCommentRepository.findById(postCommentId)
                .orElseThrow(() -> new PostNotFound("해당 댓글이 존재하지 않습니다."));

        if (comment.getAccount().getId().equals(account.getId())) {
            throw new AccessDeniedException("게시글의 삭제 권한이 없습니다.");
        }
        postCommentRepository.delete(comment);

    }

}