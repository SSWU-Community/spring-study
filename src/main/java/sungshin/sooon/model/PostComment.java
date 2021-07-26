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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "FK_Comment_Board"))
    private Post post;

    private Long order_num;

    @Column(nullable = false)
    private Boolean is_anonymous;

    private LocalDateTime created_at;

    public void mappingBoardAndAccount(Post post_id, Account account_id) {
        this.post = post_id;
        this.account = account_id;

        post_id.mappingComment(this);
        account_id.mappingComment(this);
    }
}
