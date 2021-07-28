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
import sungshin.sooon.dto.PostResponseDto;
import sungshin.sooon.service.PostCommentService;
import sungshin.sooon.service.PostService;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts/{commentId}/comments") //https://docs.spring.io/spring-framework/docs/3.0.0.M3/reference/html/ch18s02.html
@Validated
public class PostCommentController {
    private final PostCommentService postCommentService;
    private final PostService postService;

    @GetMapping("")
    public ResponseEntity findAll() {
        List<CommentResponseDto> postComments = postCommentService.findAll();
        return new ResponseEntity(postComments, HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity findById(@PathVariable long postId, @PathVariable long commentId) {
        Post post = postService.findByIdOrThrowNotFoundException(postId);
        PostResponseDto post = postCommentService.findById(commentId);
        return new ResponseEntity(post, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity save(@CurrentUser Account account, @PathVariable long postId, @Valid @RequestBody CommentRequestDto CommentRequestDto) {
        return new ResponseEntity(postCommentService.save(account, post, CommentRequestDto), HttpStatus.CREATED);
    }


    @PutMapping("/{commentId}")
    public ResponseEntity update(@CurrentUser Account account, @PathVariable long postId, @PathVariable long commentId, @Valid @RequestBody CommentRequestDto CommentRequestDto) {
        return new ResponseEntity(postCommentService.update(account, post, CommentRequestDto), HttpStatus.OK);
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity delete(@CurrentUser Account account, @PathVariable long postId, @PathVariable long commentId) {
        postCommentService.delete(account, postId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
