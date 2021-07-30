package sungshin.sooon.dto;

import lombok.*;
import sungshin.sooon.domain.post.Post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PostRequestDto {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private boolean is_anonymous;

    public Post toPost(){
        return Post.builder()
                .title(title)
                .content(content)
                .is_anonymous(is_anonymous)
                .created_at(LocalDateTime.now())
                .build();
    }

}
