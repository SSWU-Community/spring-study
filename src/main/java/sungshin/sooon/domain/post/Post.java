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
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean is_anonymous;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime created_at;

    public void setAccount(Account account){
        if(account != null)
            this.account = account;
    }

    public void update(String title, String content, Boolean is_anonymous){
        this.title = title;
        this.content = content;
        this.is_anonymous = is_anonymous;
    }
}
