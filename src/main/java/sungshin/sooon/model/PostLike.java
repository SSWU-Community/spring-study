package sungshin.sooon.model;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;


import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@Builder
@EqualsAndHashCode(of = "post_like_id")
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class PostLike {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long post_like_id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id", foreignKey = @ForeignKey(name = "FK_PostLike_Account"))
    private Account account_id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "FK_PostLike_Board"))
    private Post post_id;


    public void mappingAccount(Account account) {
        this.account_id = account;
        account.mappingPostLike(this);
    }

    public void mappingBoard(Post post) {
        this.post_id = post;
        post.mappingPostLike(this);
    }

}
