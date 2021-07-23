package sungshin.sooon.model;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@EqualsAndHashCode(of = "post_comment_id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class PostComment {

    @Id @GeneratedValue
    private Long post_comment_id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id", foreignKey = @ForeignKey(name = "FK_Comment_Account"))
    private Account account_id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "FK_Comment_Board"))
    private Post post_id;

    private Long order_num;

    @Column(nullable = false)
    private Boolean is_anonymous;

    private LocalDateTime created_at;

    public void mappingBoardAndAccount(Post post_id, Account account_id) {
        this.post_id = post_id;
        this.account_id = account_id;

        post_id.mappingComment(this);
        account_id.mappingComment(this);
    }
}
