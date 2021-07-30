package sungshin.sooon.dto;

import lombok.*;
import sungshin.sooon.domain.entity.Post;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class PostResponseDto {
    private long id;
    private String title;
    private String content;
    private boolean anonymous;
    private long likesCount;
    private LocalDateTime createdAt;

    private long accountId;
    private String accountNickname;

    //save할때는 requestDto를 entity로, find를 할 때는 entity를 responseDto로 전환하는 과정을 거치게 됩니다.
    public static PostResponseDto of(Post post) {
        return PostResponseDto
                .builder()
                .id(post.getId())
                .accountNickname(post.getAccount().getNickname())
                .accountId(post.getAccount().getId())
                .createdAt(post.getCreatedAt())
                .title(post.getTitle())
                .content(post.getContent())
                .anonymous(post.isAnonymous())
                .likesCount(post.getLikeCount())
                .build();
    }
}