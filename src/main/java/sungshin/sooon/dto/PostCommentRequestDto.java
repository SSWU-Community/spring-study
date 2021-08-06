package sungshin.sooon.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import sungshin.sooon.model.PostComment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PostCommentRequestDto {

    @NotBlank(message = "Comment should not be blank")
    @Length(max = 100)
    private String comment;

    @NotNull
    private Boolean isAnonymous;

    public PostComment toComment() {
        return PostComment.builder()
                .comment(comment)
                .isAnonymous(isAnonymous)
                .build();
    }
}
