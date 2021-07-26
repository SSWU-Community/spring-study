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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "FK_PostLike_Board"))
    private Post post;


    public void mappingAccount(Account account) {
        this.account = account;
        account.mappingPostLike(this);
    }

    public void mappingBoard(Post post) {
        this.post = post;
        post.mappingPostLike(this);
    }

}
