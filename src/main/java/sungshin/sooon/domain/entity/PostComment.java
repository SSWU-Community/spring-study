package sungshin.sooon.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostComment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_comment_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY) //코멘트에서 post를 직접 조회할 일은 없다.
    @JoinColumn(name = "post_id")
    private Post post;

    public void setPost(Post post) {
        this.post = post;
    }

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public void setAccount(Account account) {
        this.account = account;
    }

    @Column
    private long orderNum; //익명1, 익명2 구분에 사용

    @Column(nullable = false, columnDefinition = "boolean default true")
    @Builder.Default
    private boolean isAnonymous = true;

    public void update(String comment, boolean isAnonymous) {
        this.comment = comment;
        this.isAnonymous = isAnonymous;
    }
}
