package sungshin.sooon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sungshin.sooon.dto.PostRequestDto;
import sungshin.sooon.dto.PostResponseDto;
import sungshin.sooon.model.Account;
import sungshin.sooon.model.CurrentUser;
import sungshin.sooon.service.PostService;

import java.nio.file.AccessDeniedException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    // 전체 글 리스트

    // 게시글 아이디로 글 찾기
    @GetMapping("/{postId}")
    public ResponseEntity findById(@PathVariable Long postId) {
        PostResponseDto post = postService.findById(postId);
        return new ResponseEntity(post, HttpStatus.OK);
    }

    // 게시글 저장
    @PostMapping("")
    public ResponseEntity createPost(@CurrentUser Account account, @RequestBody PostRequestDto postRequestDto) {
        return new ResponseEntity(postService.save(account, postRequestDto), HttpStatus.CREATED);

    }

    // 게시글 상세
    @GetMapping("/{postId}")
    public ResponseEntity getBoardInformation(@PathVariable("postId") Long postId) {
        return new ResponseEntity(postService.read(postId), HttpStatus.OK);
    }

    // 게시글 수정
    @PutMapping("{postId}")
    public ResponseEntity updateBoard(@CurrentUser Account account, @PathVariable("postId") Long post_id, PostRequestDto postRequestDto) throws AccessDeniedException {
        return new ResponseEntity(postService.update(account, post_id, postRequestDto), HttpStatus.OK);
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity delete(@CurrentUser Account account, @PathVariable Long postId) throws AccessDeniedException {
        postService.delete(account, postId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // 게시글 좋아요
    @RequestMapping("/{postId}/like")
    public ResponseEntity saveLike(@CurrentUser Account account, @PathVariable Long postId) {
        postService.like(account, postId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
