package sungshin.sooon.domain.post;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode(of ="post_images_id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class PostImages {

    @Id @GeneratedValue
    private Long post_images_id;

    @Column(nullable = false)
    private String image_url;

    private Long order_num;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;
}
