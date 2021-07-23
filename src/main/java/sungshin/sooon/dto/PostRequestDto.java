package sungshin.sooon.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import sungshin.sooon.model.Account;
import sungshin.sooon.model.Post;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
@RequiredArgsConstructor
public class PostRequestDto {

    @NotBlank(message = "Title should not be blank")
    private String title;

    @NotBlank(message = "Content should not be blank")
    private String content;

    private Account account;

    private Boolean is_anonymous;

    private LocalDateTime created_at;

//    private List<Comment> commentList;

    private Long likeCount;

    private Long commentCount;

    public Post toBoard() {
        Post build = Post.builder()
                .title(this.title)
                .content(this.content)
                .account(this.account)
                .is_anonymous(this.is_anonymous)
                .created_at(LocalDateTime.now())
                .likeCount(this.getLikeCount())
                .build();
        return build;
    }
}
