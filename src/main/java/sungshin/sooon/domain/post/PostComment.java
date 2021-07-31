package sungshin.sooon.domain.post;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import sungshin.sooon.domain.account.Account;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostComment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_comment_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean isAnonymous;

    @Column(nullable = false)
    private Long orderNum;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;

    public void setPost(Post post) {
        this.post = post;

        if(!post.getPostComments().contains(this)) {
            post.getPostComments().add(this);
        }
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_id")
    private Account account;

    public void setAccount(Account account) {
        this.account = account;

        if(!account.getPostComments().contains(this)) {
            account.getPostComments().add(this);
        }
    }

    @Override
    public String toString() {
        return "PostComment{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", isAnonymous=" + isAnonymous +
                ", orderNum=" + orderNum +
                ", createdAt=" + createdAt +
                ", post=" + post +
                ", account=" + account +
                '}';
    }
}
