package sungshin.sooon.domain.post;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import sungshin.sooon.domain.account.Account;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class PostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_comment_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;

    public void setAccount(Account account){
        this.account = account;
    }

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;

    public void setPost(Post post){
        this.post = post;
    }

    @Column
    private Long orderNum;

    public void setOrderNum(Long orderNum){
        this.orderNum = orderNum;
    }

    @Column(nullable = false, columnDefinition = "boolean default true")
    @Builder.Default
    private Boolean isAnonymous = true;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;
}
