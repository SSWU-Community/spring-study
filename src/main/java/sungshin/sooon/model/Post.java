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

    @Column(nullable = false)
    private Boolean is_anonymous;

    @Column
    private LocalDateTime created_at;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id", foreignKey = @ForeignKey(name = "FK_account_post"))
    private Account account;

    public void setAccount(Account account) {
        this.account = account;

        if(!account.getPosts().contains(this)) {
            account.getPosts().add(this);
        }
    }

    @OneToMany(fetch = LAZY, mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<PostComment> postComments = new ArrayList<>();

    public void addPostComment(PostComment postComment) {
        this.postComments.add(postComment);

        if(postComment.getPost() != this) {
            postComment.setPost(this);
            postComment.setAccount(account);
        }
    }

    @OneToMany(fetch = LAZY, mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<PostLike> postLikes = new ArrayList<>();

    public void addPostLike(PostLike postLike) {
        this.postLikes.add(postLike);

        if(postLike.getPost() != this) {
            postLike.setPost(this);
            postLike.setAccount(account);
        }
    }

    @OneToMany(mappedBy = "post")
    private List<PostImages> postImages = new ArrayList<>();

    public void addPostImages(PostImages postImages) {
        this.postImages.add(postImages);

        if(postImages.getPost() != this) {
            postImages.setPost(this);
        }
    }

    public void update(String title, String content, Boolean is_anonymous) {
        this.title = title;
        this.content = content;
        this.is_anonymous = is_anonymous;
    }

    @Builder.Default
    @Transient
    private Long likeCount = 0L;

    public void updateLikeCount() {
        this.likeCount = (long) this.postLikes.size();
    }

    public void discountLike(PostLike postLike) {
        this.postLikes.remove(postLike);
    }

    @Transient
    private Long commentCount = 0L;

    public void updateCommentCount() {
        this.commentCount = (long) this.postComments.size();
    }

}
