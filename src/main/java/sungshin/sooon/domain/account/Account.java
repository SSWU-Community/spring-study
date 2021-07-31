package sungshin.sooon.domain.account;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GeneratorType;
import sungshin.sooon.domain.post.Post;
import sungshin.sooon.domain.post.PostComment;
import sungshin.sooon.domain.post.PostLike;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private LocalDateTime registeredDateTime;

    @OneToMany(mappedBy = "account")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    private List<PostComment> postComments = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    private List<PostLike> postLikes = new ArrayList<>();

    /**
     *
     * 기타 추가할 컬럼(필드)들 추가해주시면 됩니다~
     *
     */
}
