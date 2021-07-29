package sungshin.sooon.model;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class PostImages {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_images_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Long orderNum;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "FK_Image_Board"))
    private Post post;

    public void setPost(Post post) {
        this.post = post;

        if(!post.getPostImages().contains(this)) {
            post.getPostImages().add(this);
        }
    }


}
