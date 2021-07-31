package sungshin.sooon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import sungshin.sooon.domain.account.Account;
import sungshin.sooon.domain.post.*;
import sungshin.sooon.dto.PostCommentRequestDto;
import sungshin.sooon.dto.PostRequestDto;
import sungshin.sooon.dto.PostResponseDto;
import sungshin.sooon.util.exception.DuplicateException;
import sungshin.sooon.util.exception.ForbiddenException;
import sungshin.sooon.util.exception.ResourceNotFoundException;
import sungshin.sooon.util.exception.ResultCode;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostCommentRepository postCommentRepository;

    // 포스트 생성
    @Transactional
    public void create(Account account, PostRequestDto postRequestDto) throws EntityExistsException {
        Post post = postRequestDto.toPost();
        post.setAccount(account);
        postRepository.save(post);
    }

    // 포스트 목록 조회
    @Transactional(readOnly = true)
    public Page<PostResponseDto> readAll(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        Page<PostResponseDto> data = posts.map(PostResponseDto::from);
        return data;
    }

    // 포스트 상세 조회
    @Transactional(readOnly = true)
    public PostResponseDto readOne(Long postId) {
        return PostResponseDto.from(postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(ResultCode.POST_NOT_FOUND)));
    }

    // 포스트 삭제
    @Transactional
    public void delete(Account account, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(ResultCode.POST_NOT_FOUND));
        if(account == post.getAccount()) {
            postRepository.delete(post);
        } else {
            throw new ForbiddenException(ResultCode.FORBIDDEN_MEMBER);
        }
    }

    // 포스트 좋아요
    @Transactional
    public void createLike(Account account, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(ResultCode.POST_NOT_FOUND));
        if(postLikeRepository.existsByAccountAndPost(account, post)) {
            throw new DuplicateException(ResultCode.LIKE_DUPLICATION);
        } else {
            PostLike postlike = new PostLike();
            postlike.setAccount(account);
            postlike.setPost(post);
            postLikeRepository.save(postlike);
        }
    }

    // 포스트 댓글 생성
    @Transactional
    public void createComment(Account account, PostCommentRequestDto postCommentRequestDto) {
        Long postId = postCommentRequestDto.getPostId();
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(ResultCode.POST_NOT_FOUND));
        PostComment postComment = postCommentRequestDto.toPostComment();
        postComment.setAccount(account);
        postComment.setPost(post);
        postCommentRepository.save(postComment);
    }
}
