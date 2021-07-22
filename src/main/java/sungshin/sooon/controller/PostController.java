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

    @GetMapping("/")
    public ResponseEntity findAll() {
        List<PostResponseDto> posts = postService.findAll();
        return new ResponseEntity(posts, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable long id) {
        PostResponseDto post = postService.findById(id);
        return new ResponseEntity(post, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity save(@CurrentUser Account account, @Valid @RequestBody PostRequestDto postRequestDto) {
        return new ResponseEntity(postService.save(account, postRequestDto), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity update(@CurrentUser Account account, @PathVariable long id, @Valid @RequestBody PostRequestDto postRequestDto) {
        return new ResponseEntity(postService.update(account, id, postRequestDto),HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity delete(@CurrentUser Account account, @PathVariable long id) {
        postService.delete(account, id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /*
    @PostMapping("/{id}/likes")
    public ResponseEntity like(@PathVariable long id)
    {
        postService.like(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/likes")
    public ResponseEntity unlike(@PathVariable long id)
    {
        postService.unlike(id);
        return new ResponseEntity(HttpStatus.OK);
    }*/
}
