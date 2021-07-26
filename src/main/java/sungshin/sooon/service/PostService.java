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
import sungshin.sooon.exception.PostNotFound;
import sungshin.sooon.model.Account;
import sungshin.sooon.model.Post;
import sungshin.sooon.model.PostComment;
import sungshin.sooon.model.PostLike;
import sungshin.sooon.repository.AccountRepository;
import sungshin.sooon.repository.PostCommentRepository;
import sungshin.sooon.repository.PostRepository;
import sungshin.sooon.repository.PostLikeRepository;

import javax.transaction.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final AccountRepository accountRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostCommentRepository postCommentRepository;

    private Post getPostInService(Long post_id) {
        Post post = postRepository.findById(post_id)
                .orElseThrow(() -> new PostNotFound("해당 게시물이 존재하지 않습니다."));
        return post;
    }

    private Account getUserInService(Account user) {
        Account account = accountRepository.findById(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("게시글 작성 권한이 없습니다."));
        return account;
    }

    // 게시글 전체 리스트
    public Page<Post> getList(Pageable pageable) {
        int page = pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1;
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "post_id"));

        return postRepository.findAll(pageable);
    }

    // CREATE
    @Transactional
    public PostResponseDto save(Account account, PostRequestDto postRequestDto) {
        Post post = postRequestDto.toPost();
        post.setAccount(account);
        return PostResponseDto.from(postRepository.save(post));
    }

    // READ
    @Transactional
    public PostResponseDto read(Long post_id) {
        return PostResponseDto.from(postRepository.findById(post_id)
                .orElseThrow(() -> new PostNotFound("해당 게시물이 존재하지 않습니다.")));
    }

    // UPDATE
    @Transactional
    public PostResponseDto update(Account account, Long post_id, PostRequestDto postRequestDto) throws AccessDeniedException {
        Post post = postRepository.findById(post_id)
                .orElseThrow(() -> new PostNotFound("해당 게시물이 존재하지 않습니다."));

        // + 운영자 권한일경우 삭제가능 구현해야됨
        if (post.getAccount().getId().equals(account.getId())) {
            throw new AccessDeniedException("게시글의 수정 권한이 없습니다.");
        }
        postRequestDto.apply(post);
        return PostResponseDto.from(post);
    }

    // DELETE
    @Transactional
    public void delete(Account account, Long post_id) throws AccessDeniedException {
        Post post = postRepository.findById(post_id)
                .orElseThrow(() -> new PostNotFound("해당 게시물이 존재하지 않습니다."));

        if (post.getAccount().getId().equals(account.getId())) {
            throw new AccessDeniedException("게시글의 삭제 권한이 없습니다.");
        }
        postRepository.delete(post);

    }

    // 좋아요
    @Transactional
    public void like(Account user, Long post_id) {
        Post post = getPostInService(post_id);
        Account account = getUserInService(user);

        Optional<PostLike> byPostAndUser = postLikeRepository.findByPostAndUser(post, account);

        byPostAndUser.ifPresentOrElse(
                post_like -> {
                    postLikeRepository.delete(post_like);
                    post.discountLike(post_like);
                },
                () -> {
                    PostLike post_like = PostLike.builder().build();

                    post_like.mappingBoard(post);
                    post_like.mappingAccount(account);
                    post.updateLikeCount();

                    postLikeRepository.save(post_like);
                }
        );
    }

    // 댓글 생성
    @Transactional
    public PostCommentResponseDto savePostComment(Account account, PostCommentRequestDto postCommentRequestDto) {
        PostComment comment = postCommentRequestDto.toComment();
        comment.setAccount(account);
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