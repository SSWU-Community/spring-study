package sungshin.sooon.dto;

import lombok.Builder;
import lombok.Data;
import sungshin.sooon.domain.post.PostComment;

import java.time.LocalDateTime;

@Data
@Builder
public class PostCommentResponseDto {
    private Long id;
    private String comment;
    private Long orderNum;
    private boolean isAnonymous;
    private LocalDateTime createdAt;

    private Long postId;
    private Long accountId;

    public static PostCommentResponseDto of(PostComment comment){
        return PostCommentResponseDto.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .orderNum(comment.getOrderNum())
                .isAnonymous(comment.getIsAnonymous())
                .createdAt(comment.getCreatedAt())
                .postId(comment.getPost().getId())
                .accountId(comment.getAccount().getId())
                .build();
    }
}
