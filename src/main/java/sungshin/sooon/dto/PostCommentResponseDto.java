package sungshin.sooon.dto;

import lombok.*;
import sungshin.sooon.model.PostComment;

import java.time.LocalDateTime;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentResponseDto {

    private Long post_comment_id;

    private String comment;

    private String nickname;

    private Boolean is_anonymous;

    private Long order_num;

    private LocalDateTime created_at;

    public static PostCommentResponseDto from(PostComment postComment) {
        return PostCommentResponseDto.builder()
                .post_comment_id(postComment.getId())
                .nickname(postComment.getAccount().getNickname())
                .comment(postComment.getComment())
                .is_anonymous(postComment.getIs_anonymous())
                .order_num(postComment.getId())
                .created_at(postComment.getCreated_at())
                .build();
    }
}
