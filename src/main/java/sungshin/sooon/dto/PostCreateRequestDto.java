package sungshin.sooon.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sungshin.sooon.domain.entity.Post;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateRequestDto {
    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "content is required")
    private String content;

    @NotNull(message = "isAnonymous is required")
    private boolean isAnonymous;
    
    public Post toPost() {
        return Post.builder()
                .title(title)
                .content(content)
                .isAnonymous(isAnonymous)
                .build();
    }
}
