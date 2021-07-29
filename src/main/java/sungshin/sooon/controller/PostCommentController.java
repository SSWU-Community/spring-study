package sungshin.sooon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sungshin.sooon.domain.entity.Account;
import sungshin.sooon.domain.entity.CurrentUser;
import sungshin.sooon.domain.entity.Post;
import sungshin.sooon.dto.CommentRequestDto;
import sungshin.sooon.dto.CommentResponseDto;
import sungshin.sooon.service.PostCommentService;
import sungshin.sooon.service.PostService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts/{postId}/comments")
//질문: findAllbyPost와 save, update를 제외하고는 comment의 id값은 post와는 독립적인데 이렇게 하위 리소스로 구분시켜도 되나?
//그러니까 post의 1번 댓글 posts/{postId}/comments/1 이 아니라 포스트의 몇번째 댓글인지 상관없이 아이디값이 결정되므로 posts/{postId}/comments/412 이런식으로 되는데 괜찮은건가?
//https://docs.spring.io/spring-framework/docs/3.0.0.M3/reference/html/ch18s02.html
@Validated
public class PostCommentController {
    private final PostCommentService postCommentService;
    private final PostService postService;

    @GetMapping("")
    public ResponseEntity findAllByPost(@PathVariable long postId) {
        Post post = postService.findByIdOrThrowNotFoundException(postId);
        List<CommentResponseDto> postComments = postCommentService.findAllByPost(post);
        return new ResponseEntity(postComments, HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity findById(@PathVariable long postId, @PathVariable long commentId) {
        postService.findByIdOrThrowNotFoundException(postId);
        return new ResponseEntity(postCommentService.findById(commentId), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity save(@CurrentUser Account account, @PathVariable long postId, @Valid @RequestBody CommentRequestDto CommentRequestDto) {
        Post post = postService.findByIdOrThrowNotFoundException(postId);
        return new ResponseEntity(postCommentService.save(account, post, CommentRequestDto), HttpStatus.CREATED);
    }


    @PutMapping("/{commentId}")
    public ResponseEntity update(@CurrentUser Account account, @PathVariable long postId, @PathVariable long commentId, @Valid @RequestBody CommentRequestDto CommentRequestDto) {
        Post post = postService.findByIdOrThrowNotFoundException(postId);
        return new ResponseEntity(postCommentService.update(account, post, commentId, CommentRequestDto), HttpStatus.OK);
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity delete(@CurrentUser Account account, @PathVariable long postId, @PathVariable long commentId) {
        postService.findByIdOrThrowNotFoundException(postId);
        postCommentService.delete(account, commentId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
