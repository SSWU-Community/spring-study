package sungshin.sooon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sungshin.sooon.domain.account.Account;
import sungshin.sooon.domain.account.CurrentUser;
import sungshin.sooon.domain.post.Post;
import sungshin.sooon.dto.PostCommentRequestDto;
import sungshin.sooon.dto.PostRequestDto;
import sungshin.sooon.dto.PostResponseDto;
import sungshin.sooon.service.PostService;
import sungshin.sooon.util.exception.Result;
import sungshin.sooon.util.exception.ResultCode;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    // 포스트 생성
    @PostMapping("")
    public ResponseEntity<Result> create(@CurrentUser Account account, @RequestBody @Valid PostRequestDto postRequestDto) {
        postService.create(account, postRequestDto);
        return Result.toResult(ResultCode.POST_CREATE_SUCCESS);
    }

    // 포스트 목록 조회
    @GetMapping("")
    public ResponseEntity<Result<Page<PostResponseDto>>> readAll(Pageable pageable) {
        Page<PostResponseDto> data = postService.readAll(pageable);
        return Result.toResult(ResultCode.POST_READ_SUCCESS, data);
    }

    // 포스트 상세 조회
    @GetMapping("/{postId}")
    public ResponseEntity<Result<PostResponseDto>> readOne(@PathVariable Long postId) {
        PostResponseDto data = postService.readOne(postId);
        return Result.toResult(ResultCode.POST_READ_SUCCESS, data);
    }

    // 포스트 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Result> deleteOne(@CurrentUser Account account, @PathVariable Long postId) {
        postService.delete(account, postId);
        return Result.toResult(ResultCode.POST_DELETE_SUCCESS);
    }

    // 포스트 좋아요
    @PostMapping("/{postId}/like")
    public ResponseEntity<Result> createLike(@CurrentUser Account account, @PathVariable Long postId) {
        postService.createLike(account, postId);
        return Result.toResult(ResultCode.POST_LIKE_CREATE_SUCCESS);
    }

    // 포스트 댓글 생성
    @PostMapping("/{postId}/comment")
    public ResponseEntity<Result> createComment(@CurrentUser Account account, @PathVariable Long postId, @RequestBody PostCommentRequestDto postCommentRequestDto) {
        postService.createComment(account, postCommentRequestDto);
        return Result.toResult(ResultCode.POST_COMMENT_CREATE_SUCCESS);
    }
}
