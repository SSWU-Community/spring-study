package sungshin.sooon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sungshin.sooon.domain.entity.Post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRequestDto {
    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "content is required")
    private String content;

    @NotNull(message = "anonymous is required")
    private boolean anonymous;

    public Post toPost() {
        return Post.builder()
                .title(title)
                .content(content)
                .isAnonymous(anonymous)
                .build();
    }

    public void apply(Post post) {
        post.update(title, content, anonymous);
        // https://github.com/hojinDev/restdocs-sample/blob/d820195792af03057670552341b9f8e5f9d1b0b3/src/main/java/com/example/demo/service/dto/UnitUpdateDto.java#L21
    }
}
