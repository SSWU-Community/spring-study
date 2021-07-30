package sungshin.sooon.domain.post;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class PostImages {
    @Id
    @GeneratedValue
    @Column(name = "post_images_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;

    private String image_url;

    private Long order_num;
}
