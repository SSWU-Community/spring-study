package sungshin.sooon.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sungshin.sooon.domain.account.Account;
import sungshin.sooon.domain.post.Post;
import sungshin.sooon.domain.post.PostComment;
import sungshin.sooon.domain.post.PostCommentRepository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {

    private Long id;

    private String title;

    private String content;

    private boolean isAnonymous;

    private LocalDateTime createdAt;

    private AccountResponseDto account;

    private List<PostComment> postComments;

    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .isAnonymous(post.isAnonymous())
                .createdAt(post.getCreatedAt())
                .account(AccountResponseDto.from(post.getAccount()))
                .build();
    }
}
