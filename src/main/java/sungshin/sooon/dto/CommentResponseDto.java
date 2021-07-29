package sungshin.sooon.dto;

import lombok.Builder;
import lombok.Data;
import sungshin.sooon.domain.entity.PostComment;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponseDto {
    private long id;
    private String comment;
    private boolean anonymous;
    private long anonymousNum;
    private LocalDateTime createdAt;

    private long postId;
    private long accountId;
    private String accountNickname;


    public static CommentResponseDto of(PostComment postComment) {
        return CommentResponseDto
                .builder()
                .id(postComment.getId())
                .comment(postComment.getComment())
                .anonymous(postComment.isAnonymous())
                .anonymousNum(postComment.getOrderNum())
                .createdAt(postComment.getCreatedAt())
                .postId(postComment.getPost().getId())
                .accountId(postComment.getAccount().getId())
                .accountNickname(postComment.getAccount().getNickname())
                .build();
    }
}
