package sungshin.sooon.domain.post;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import sungshin.sooon.domain.account.Account;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_like_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;

    public void setPost(Post post) {
        this.post = post;

        if(!post.getPostLikes().contains(this)) {
            post.getPostLikes().add(this);
        }
    }

    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;

    public void setAccount(Account account) {
        this.account = account;

        if(!account.getPostLikes().contains(this)) {
            account.getPostLikes().add(this);
        }
    }

}
