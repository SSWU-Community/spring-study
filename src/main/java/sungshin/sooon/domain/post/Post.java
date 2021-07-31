package sungshin.sooon.domain.post;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import sungshin.sooon.domain.account.Account;

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
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean isAnonymous = true;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public void setAccount(Account account) {
        this.account = account;

        if(!account.getPosts().contains(this)) {
            account.getPosts().add(this);
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

    @OneToMany(mappedBy = "post")
    private List<PostComment> postComments = new ArrayList<>();

    public void addPostComment(PostComment postComment) {
        this.postComments.add(postComment);

        if(postComment.getPost() != this) {
            postComment.setPost(this);
            postComment.setAccount(account);
        }
    }

    @OneToMany(mappedBy = "post")
    private List<PostLike> postLikes = new ArrayList<>();

    public void addPostLike(PostLike postLike) {
        this.postLikes.add(postLike);

        if(postLike.getPost() != this) {
            postLike.setPost(this);
            postLike.setAccount(account);
        }
    }

}
