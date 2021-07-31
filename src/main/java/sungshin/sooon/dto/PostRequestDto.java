package sungshin.sooon.dto;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import sungshin.sooon.domain.account.Account;
import sungshin.sooon.domain.post.Post;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private boolean isAnonymous;

    public Post toPost() {
        return Post.builder()
                .title(title)
                .content(content)
                .isAnonymous(isAnonymous)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
