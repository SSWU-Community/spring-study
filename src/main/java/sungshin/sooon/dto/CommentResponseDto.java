package sungshin.sooon.dto;

import sungshin.sooon.domain.entity.Post;
import sungshin.sooon.domain.entity.PostComment;

import java.time.LocalDateTime;

public class CommentResponseDto {
    private long commentId;

    private String comment;
    private boolean isAnonymous;
    private long commentId;
    private LocalDateTime createdAt;

    private long authorId;
    private String authorNickname;

    //save할때는 requestDto를 entity로, find를 할 때는 entity를 responseDto로 전환하는 과정을 거치게 됩니다.
    public static PostResponseDto of(Post post) {
        return PostResponseDto
                .builder()
                .id(post.getId())
                .nickname(post.getAccount().getNickname())
                .createdAt(post.getCreatedAt())
                .title(post.getTitle())
                .content(post.getContent())
                .isAnonymous(post.isAnonymous())
                .likesCount(post.getLikesCount())
                .build();
    }
}
