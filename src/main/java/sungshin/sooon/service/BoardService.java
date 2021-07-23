package sungshin.sooon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sungshin.sooon.dto.PostRequestDto;
import sungshin.sooon.dto.PostResponseDto;
import sungshin.sooon.dto.PostUpdateRequestDto;
import sungshin.sooon.model.Account;
import sungshin.sooon.model.Post;
import sungshin.sooon.model.PostLike;
import sungshin.sooon.repository.AccountRepository;
import sungshin.sooon.repository.BoardRepository;
import sungshin.sooon.repository.PostLikeRepository;

import javax.transaction.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final AccountRepository accountRepository;
    private final BoardRepository boardRepository;
    private final PostLikeRepository postLikeRepository;

    // 게시글 전체 리스트
    public Page<Post> getPostList(Pageable pageable) {
        int page = pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1;
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "post_id"));

        return boardRepository.findAll(pageable);
    }

    // CREATE
    @Transactional
    public Long savePost(PostRequestDto postRequestDto) {
        Post post = postRequestDto.toBoard();
        return boardRepository.save(post).getPost_id();
    }

    // READ
    @Transactional
    public PostResponseDto readPost(Long post_id) {
        Post post = boardRepository.findById(post_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        PostResponseDto responseDto = new PostResponseDto(post);

        return responseDto;
    }

    // UPDATE
    @Transactional
    public Long updatePost(Long post_id, PostUpdateRequestDto updateDto) {
        Post post = boardRepository.findById(post_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        post.update(updateDto.getTitle(), updateDto.getContent(), updateDto.getIs_anonymous(), updateDto.getCreated_at());

        return post_id;
    }

    // DELETE
    @Transactional
    public void deletePost(Long post_id) {
        Post post = boardRepository.findById(post_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        boardRepository.delete(post);
    }

    // 좋아요
    @Transactional
    public void like(Long post_id, String email) {
        Post post = boardRepository.findById(post_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));
        Account account = accountRepository.findByEmail(email);
        if(account == null) {
            throw new UsernameNotFoundException(email);
        }
        Optional<PostLike> byPostAndUser = postLikeRepository.findByPostAndUser(post, account);

        byPostAndUser.ifPresentOrElse(
                post_like -> {
                    postLikeRepository.delete(post_like);
                    post.discountLike(post_like);
                },
                () -> {
                    PostLike post_like = PostLike.builder().build();

                    post_like.mappingBoard(post);
                    post_like.mappingAccount(account);
                    post.updateLikeCount();

                    postLikeRepository.save(post_like);
                }
        );
    }

}