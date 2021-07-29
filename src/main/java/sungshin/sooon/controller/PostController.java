package sungshin.sooon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sungshin.sooon.dto.PostCommentRequestDto;
import sungshin.sooon.dto.PostRequestDto;
import sungshin.sooon.model.Account;
import sungshin.sooon.model.CurrentUser;
import sungshin.sooon.service.AmazonS3Service;
import sungshin.sooon.service.PostService;

import java.nio.file.AccessDeniedException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;
    private final AmazonS3Service amazonS3Service;

    // 전체 글 리스트

    // 게시글 저장
    @PostMapping("")
    public ResponseEntity createPost(@CurrentUser Account account, @RequestBody PostRequestDto postRequestDto) {
        return new ResponseEntity(postService.save(account, postRequestDto), HttpStatus.CREATED);

    }

    // 게시글 상세
    @GetMapping("/{post_id}")
    public ResponseEntity getBoardInformation(@PathVariable("post_id") Long postId) {
        return new ResponseEntity(postService.read(postId), HttpStatus.OK);
    }

    // 게시글 수정
    @PutMapping("{post_id}")
    public ResponseEntity updateBoard(@CurrentUser Account account, @PathVariable("post_id") Long post_id, PostRequestDto postRequestDto) throws AccessDeniedException {
        return new ResponseEntity(postService.update(account, post_id, postRequestDto), HttpStatus.OK);
    }

    // 게시글 삭제
    @DeleteMapping("/{post_id}")
    public ResponseEntity delete(@CurrentUser Account account, @PathVariable Long postId) throws AccessDeniedException {
        postService.delete(account, postId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // 게시글 좋아요
    @RequestMapping("/{post_id}/like")
    public ResponseEntity saveLike(@CurrentUser Account account, @PathVariable Long postId) {
        postService.like(account, postId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // 댓글 저장
    @PostMapping("/{post_id}/comment/{comment_id}")
    public ResponseEntity savePostComment(@CurrentUser Account account, @PathVariable Long postId, @RequestBody PostCommentRequestDto postCommentRequestDto){
        return new ResponseEntity(postService.savePostComment(account, postId, postCommentRequestDto), HttpStatus.CREATED);
    }

    // 댓글 삭제
    @DeleteMapping("/{post_id}/comment")
    public ResponseEntity deletePostComment(@CurrentUser Account account, @RequestBody Long postCommentId) throws AccessDeniedException {
        postService.deletePostComment(account, postCommentId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
