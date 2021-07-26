package sungshin.sooon.model;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Account {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false)
    private Long id;

    @Column(unique = true, length = 20, nullable = false)
    private String email;

    @Column(length = 16, nullable = false)
    private String password;

    private String nickname;

    private LocalDateTime registeredDateTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.REMOVE)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.REMOVE)
    private List<PostComment> commentList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account_id", cascade = CascadeType.REMOVE)
    private List<PostLike> postLikeList = new ArrayList<>();


    public void mappingPost(Post post) {
        postList.add(post);
    }

    public void mappingComment(PostComment comment) {
        commentList.add(comment);
    }

    public void mappingPostLike(PostLike postLike) {
        postLikeList.add(postLike);
    }
}
