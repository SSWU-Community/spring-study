package sungshin.sooon.model;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class PostComment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_comment_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id", foreignKey = @ForeignKey(name = "FK_Comment_Account"))
    private Account account;

    public void setAccount(Account account) {
        this.account = account;

        if(!account.getPostComments().contains(this)) {
            account.getPostComments().add(this);
        }
    }

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "FK_Comment_Board"))
    private Post post;

    public void setPost(Post post) {
        this.post = post;

        if(!post.getPostComments().contains(this)) {
            post.getPostComments().add(this);
        }
    }

    private Long order_num;

    @Column(nullable = false)
    private Boolean is_anonymous;

    private LocalDateTime created_at;

}
