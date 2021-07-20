package sungshin.sooon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sungshin.sooon.dto.PostCreateRequestDto;
import sungshin.sooon.dto.SignupRequestDto;
import sungshin.sooon.service.AccountService;
import sungshin.sooon.service.PostService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@Validated
public class PostController {
    private final PostService postService;

    @GetMapping("/")
    public ResponseEntity findAll(@RequestParam long page)
    {
        postService.findAll(page);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable long id)
    {
        postService.findById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity create(@Valid @RequestBody PostCreateRequestDto postCreateRequestDto, @RequestPart(value="images", required=false) List<MultipartFile> images)
    {
        postService.create(postCreateRequestDto);
        return new ResponseEntity(HttpStatus.OK);
    }
/*
    @PutMapping("/{id}")
    public ResponseEntity update(@Valid @RequestBody PostUpdateReqeustDto postUpdateReqeustDto)
    {
        postService.update(postUpdateReqeustDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id)
    {
        postService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

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
