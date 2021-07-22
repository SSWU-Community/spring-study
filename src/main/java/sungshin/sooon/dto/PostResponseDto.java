package sungshin.sooon.dto;

import lombok.*;
import sungshin.sooon.domain.entity.Post;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class PostResponseDto {
    private long id;
    private String nickname;
    private LocalDateTime createdAt;
    private String title;
    private String content;
    private boolean isAnonymous;

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
                .build();
    }
}