package sungshin.sooon.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import sungshin.sooon.model.PostComment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentRequestDto {

    @NotBlank(message = "Comment should not be blank")
    @Length(max = 100)
    private String comment;

    @NotNull
    private Boolean is_anonymous;

    private LocalDateTime created_at;

    public PostComment toComment() {
        return PostComment.builder()
                .comment(comment)
                .is_anonymous(is_anonymous)
                .created_at(LocalDateTime.now())
                .build();
    }
}
