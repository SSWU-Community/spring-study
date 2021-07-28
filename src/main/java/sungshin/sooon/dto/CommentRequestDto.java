package sungshin.sooon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sungshin.sooon.domain.entity.Post;
import sungshin.sooon.domain.entity.PostComment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDto {
    @NotBlank(message = "comment is required")
    private String comment;

    @NotNull(message = "isAnonymous is required")
    private boolean isAnonymous;

    public PostComment toComment() {
        return PostComment.builder()
                .comment(comment)
                .isAnonymous(isAnonymous)
                .build();
    }

    public void apply(PostComment postComment) {
        postComment.update(comment,isAnonymous);
    }
}