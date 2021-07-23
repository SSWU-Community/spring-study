package sungshin.sooon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sungshin.sooon.dto.CommentRequestDto;
import sungshin.sooon.service.CommentService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 저장
    @PostMapping("/post/{post_id}/comment")
    public Long createComment(@PathVariable("post_id") Long post_id, @RequestBody CommentRequestDto commentDto, Principal principal) {
        return commentService.createComment(post_id, commentDto, principal.getName());
    }
}
