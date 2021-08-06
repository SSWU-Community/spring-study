package sungshin.sooon.dto;

import lombok.*;
import sungshin.sooon.model.Post;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private Boolean isAnonymous;
    private Long likesCount;
    private LocalDateTime createdAt;

    private Long accountId;
    private String nickname;
    //    private Long imageId;

    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .accountId(post.getAccount().getId())
                .nickname(post.getAccount().getNickname())
                .createdAt(post.getCreatedAt())
                .title(post.getTitle())
                .content(post.getContent())
                .isAnonymous(post.getIsAnonymous())
                .likesCount(post.getLikeCount())
//                .imageId(post.getImageId())
                .build();
    }
}
