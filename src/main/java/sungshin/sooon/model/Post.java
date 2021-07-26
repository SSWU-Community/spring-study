package sungshin.sooon.model;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id", foreignKey = @ForeignKey(name = "FK_account_post"))
    private Account account;


    @Column(nullable = false)
    private Boolean is_anonymous;

    private LocalDateTime created_at;

    @Builder.Default
    @Transient
    private Long likeCount = 0L;

    @Transient
    private Long commentCount = 0L;

    @OneToMany(fetch = LAZY, mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<PostComment> commentList = new ArrayList<>();

    @OneToMany(fetch = LAZY, mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<PostLike> postLikeList = new ArrayList<>();

    public void update(String title, String content, Boolean is_anonymous) {
        this.title = title;
        this.content = content;
        this.is_anonymous = is_anonymous;
    }

    public void mappingAccount(Account account) {
        this.account = account;
        account.mappingPost(this);
    }

    public void mappingComment(PostComment comment) {
        this.commentList.add(comment);
    }

    public void mappingPostLike(PostLike postLike) {
        this.postLikeList.add(postLike);
    }

    public void updateLikeCount() {
        this.likeCount = (long) this.postLikeList.size();
    }

    public void discountLike(PostLike postLike) {
        this.postLikeList.remove(postLike);
    }

}
