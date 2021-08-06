package sungshin.sooon.dto;

import lombok.*;
import sungshin.sooon.model.Post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Builder
public class PostRequestDto {

    @NotBlank(message = "Title should not be blank")
    private String title;

    @NotBlank(message = "Content should not be blank")
    private String content;

    @NotNull
    private Boolean isAnonymous;

    public Post toPost() {
        return Post.builder()
                .title(title)
                .content(content)
                .isAnonymous(isAnonymous)
                .build();
    }

    public void apply(Post post) {
        post.update(title, content, isAnonymous);
    }
}
