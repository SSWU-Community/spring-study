package sungshin.sooon.dto;

import lombok.*;
import sungshin.sooon.model.PostComment;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class PostCommentResponseDto {

    private Long id;
    private String comment;
    private Boolean isAnonymous;
    private Long orderNum;
    private LocalDateTime createdAt;

    private Long postId;
    private Long accountId;
    private String nickname;

    public static PostCommentResponseDto from(PostComment postComment) {
        return PostCommentResponseDto.builder()
                .id(postComment.getId())
                .comment(postComment.getComment())
                .isAnonymous(postComment.getIsAnonymous())
                .orderNum(postComment.getOrderNum())
                .createdAt(postComment.getCreatedAt())
                .postId(postComment.getPost().getId())
                .accountId(postComment.getAccount().getId())
                .nickname(postComment.getAccount().getNickname())
                .build();
    }
}
