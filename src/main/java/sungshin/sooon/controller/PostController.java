package sungshin.sooon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sungshin.sooon.dto.*;
import sungshin.sooon.domain.account.Account;
import sungshin.sooon.domain.account.CurrentUser;
import sungshin.sooon.domain.post.PostRepository;
import sungshin.sooon.service.PostService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    //게시글
    @PostMapping("")
    public ResponseEntity<PostResponseDto> create(@CurrentUser Account account, @Valid @RequestBody PostRequestDto request){
        return new ResponseEntity<>(postService.save(account, request), HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public PostResponseDto read(@PathVariable Long postId){
        return postService.read(postId);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponseDto> update(@CurrentUser Account account, @PathVariable Long postId, @Valid @RequestBody PostRequestDto request){
        return new ResponseEntity<>(postService.update(account, postId, request), HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity delete(@CurrentUser Account account, @PathVariable Long postId){
        postService.delete(account, postId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("")
    public ResponseEntity readPosts(@PageableDefault Pageable pageable){
        return new ResponseEntity<>(postService.getPostList(pageable), HttpStatus.OK);
    }

    //좋아요

    @RequestMapping("/{postId}/likes")
    public ResponseEntity like(@PathVariable Long postId, @CurrentUser Account account){
        return new ResponseEntity(postService.like(postId, account), HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/likes")
    public ResponseEntity dislike(@CurrentUser Account account, @PathVariable Long postId){
        postService.dislike(account, postId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //댓글

    @PostMapping("/{postId}/comments")
    public ResponseEntity createPostComment(@RequestBody PostCommentRequestDto requestDto, @CurrentUser Account account, @PathVariable Long postId){
        return new ResponseEntity(postService.createPostComment(account, postId, requestDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity deletePostComment(@PathVariable Long commentId, @CurrentUser Account account, @PathVariable Long postId){
        postService.deletePostComment(commentId, account, postId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
