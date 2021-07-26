package sungshin.sooon.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import sungshin.sooon.model.Post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@RequiredArgsConstructor
public class PostRequestDto {

    @NotBlank(message = "Title should not be blank")
    private String title;

    @NotBlank(message = "Content should not be blank")
    private String content;

    @NotNull
    private Boolean is_anonymous;

    private LocalDateTime created_at;

    public Post toPost() {
        return Post.builder()
                .title(title)
                .content(content)
                .is_anonymous(is_anonymous)
                .created_at(LocalDateTime.now())
                .build();
    }

    public void apply(Post post) {
        post.update(title, content, is_anonymous);
    }
}
