package sungshin.sooon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sungshin.sooon.dto.PostRequestDto;
import sungshin.sooon.dto.PostResponseDto;
import sungshin.sooon.dto.PostUpdateRequestDto;
import sungshin.sooon.model.Post;
import sungshin.sooon.service.BoardService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
public class BoardController {

    private final BoardService boardService;

    // 전체 글 리스트
    @GetMapping("/list")
    public Page<Post> list(@PageableDefault(page = 1, sort = "created_at", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        Page<Post> postList = boardService.getPostList(pageable);
        model.addAttribute("postList", postList);

        return boardService.getPostList(pageable);
    }

    // 게시글 저장
    @PostMapping("/post")
    public Long createPost(@RequestBody PostRequestDto postRequestDto) {
        return boardService.savePost(postRequestDto);
    }

    // 게시글 상세
    @GetMapping("/detail/{post_id}")
    public PostResponseDto getBoardInformation(@PathVariable("post_id") Long post_id) {
        return boardService.readPost(post_id);
    }

    // 게시글 수정
    @PutMapping("edit/{post_id}")
    public Long updateBoard(@PathVariable("post_id") Long post_id, PostUpdateRequestDto postUpdateRequestDto) {
        return boardService.updatePost(post_id, postUpdateRequestDto);
    }

    // 게시글 삭제
    @DeleteMapping("/delete/{post_id}")
    public String delete(@PathVariable Long post_id) {
        boardService.deletePost(post_id);

        return "redirect:/";
    }

    // 게시글 좋아요
    @RequestMapping("/{post_id}/like")
    public void like(@PathVariable Long post_id, Principal principal) {
        boardService.like(post_id, principal.getName());
    }

}
