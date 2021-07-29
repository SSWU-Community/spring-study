package sungshin.sooon.dto;

import lombok.*;
import sungshin.sooon.model.Account;
import sungshin.sooon.model.Post;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private Account account;
    private Boolean is_anonymous;
    private LocalDateTime created_at;

    public static PostResponseDto of(Post post){
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .account(post.getAccount())
                .is_anonymous(post.is_anonymous())
                .created_at(post.getCreated_at())
                .build();
    }

}
