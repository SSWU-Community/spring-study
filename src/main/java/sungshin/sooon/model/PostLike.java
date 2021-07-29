package sungshin.sooon.model;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;


import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class PostLike {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "post_like_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id", foreignKey = @ForeignKey(name = "FK_PostLike_Account"))
    private Account account;

    public void setAccount(Account account) {
        this.account = account;

        if(!account.getPostLikes().contains(this)) {
            account.getPostLikes().add(this);
        }
    }

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "FK_PostLike_Board"))
    private Post post;

    public void setPost(Post post) {
        this.post = post;

        if(!post.getPostLikes().contains(this)) {
            post.getPostLikes().add(this);
        }
    }

}
