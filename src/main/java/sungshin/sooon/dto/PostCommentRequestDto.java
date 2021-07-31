package sungshin.sooon.dto;

import lombok.*;
import sungshin.sooon.domain.account.Account;
import sungshin.sooon.domain.post.Post;
import sungshin.sooon.domain.post.PostComment;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostCommentRequestDto {

    private Long postId;

    private String comment;

    private boolean isAnonymous;

    public PostComment toPostComment() {
        return PostComment.builder()
                .comment(comment)
                .isAnonymous(isAnonymous)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
