package sungshin.sooon.dto;

import lombok.*;
import sungshin.sooon.domain.post.PostComment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentRequestDto {
    @NotBlank
    private String comment;

    @NotNull
    private Boolean is_anonymous;

    public PostComment toPostComment(){
        return PostComment.builder()
                .comment(comment)
                .isAnonymous(is_anonymous)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
