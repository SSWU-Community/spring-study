package sungshin.sooon.domain.post;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostImages {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_images_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    private Long orderNum;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;

    public void setPost(Post post) {
        this.post = post;

        if(!post.getPostImages().contains(this)) {
            post.getPostImages().add(this);
        }
    }
}
