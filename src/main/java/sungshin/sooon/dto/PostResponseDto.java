package sungshin.sooon.dto;

import lombok.*;
import sungshin.sooon.model.Post;

import java.time.LocalDateTime;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {

    private Long post_id;

    private String nickname;

    private String title;

    private String content;

    private Boolean is_anonymous;

    private LocalDateTime created_at;

    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
                .post_id(post.getId())
                .nickname(post.getAccount().getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .is_anonymous(post.getIs_anonymous())
                .created_at(post.getCreated_at())
                .build();
    }
}
