package sungshin.sooon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sungshin.sooon.domain.entity.Account;
import sungshin.sooon.domain.entity.CurrentUser;
import sungshin.sooon.dto.PostRequestDto;
import sungshin.sooon.dto.PostResponseDto;
import sungshin.sooon.service.PostService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@Validated
public class PostController {
    private final PostService postService;

    /*
    //내가 쓴 글 조회
    @GetMapping("/")
    public ResponseEntity findAllByAccount(@CurrentUser Account account)
    {
        List<PostResponseDto> posts = postService.findAllByAccount(account);
        return new ResponseEntity(posts,HttpStatus.OK);
    }*/

    @GetMapping("")
    public ResponseEntity findAll() {
        List<PostResponseDto> posts = postService.findAll();
        return new ResponseEntity(posts, HttpStatus.OK);
    }


    @GetMapping("/{postId}")
    public ResponseEntity findById(@PathVariable long postId) {
        PostResponseDto post = postService.findById(postId);
        return new ResponseEntity(post, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity save(@CurrentUser Account account, @Valid @RequestBody PostRequestDto postRequestDto) {
        return new ResponseEntity(postService.save(account, postRequestDto), HttpStatus.CREATED);
    }


    @PutMapping("/{postId}")
    public ResponseEntity update(@CurrentUser Account account, @PathVariable long postId, @Valid @RequestBody PostRequestDto postRequestDto) {
        return new ResponseEntity(postService.update(account, postId, postRequestDto), HttpStatus.OK);
    }


    @DeleteMapping("/{postId}")
    public ResponseEntity delete(@CurrentUser Account account, @PathVariable long postId) {
        postService.delete(account, postId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{postId}/likes/{userId}") //github style !
    public ResponseEntity findLikeByUserId(@CurrentUser Account account, @PathVariable long postId, @PathVariable long userId) {
        postService.findLikeByUserId(account, postId, userId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{postId}/likes")
    public ResponseEntity saveLike(@CurrentUser Account account, @PathVariable long postId) {
        postService.saveLike(account, postId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /*
    * github style! 쿼리가 좋을까?
    * 만약 어떤 resource를 식별하고 싶으면 Path Variable을 사용하고,
    * 정렬이나 필터링을 한다면 Query Parameter를 사용하는 것이 Best Practice이다.
    * URI는 리소스 TYPE의 특정 인스턴스를 고유하게 식별하는 리소스 식별자
    * URI should only consist of parts that will never change and will continue to uniquely identify that resource throughout its lifetime
     */
    @DeleteMapping("/{postId}/likes/{userId}")
    public ResponseEntity deleteLike(@CurrentUser Account account, @PathVariable long postId, @PathVariable long userId) {
        postService.deleteLike(account, postId, userId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
