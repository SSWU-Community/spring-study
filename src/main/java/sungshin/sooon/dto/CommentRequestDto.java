package sungshin.sooon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import sungshin.sooon.model.Account;
import sungshin.sooon.model.Post;
import sungshin.sooon.model.PostComment;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CommentRequestDto {
    private Long post_comment_id;

    @NotBlank
    @Length(max = 100)
    private String comment;

    private Account account;

    private Post post;

    private Long order_num;

    private Boolean is_anonymous;

    private LocalDateTime created_at;

    public PostComment toComment() {
        PostComment build = PostComment.builder()
                .post_comment_id(this.post_comment_id)
                .comment(this.comment)
                .account_id(this.account)
                .post_id(this.post)
                .order_num(this.order_num)
                .is_anonymous(this.is_anonymous)
                .created_at(LocalDateTime.now())
                .build();
        return build;
    }
}
