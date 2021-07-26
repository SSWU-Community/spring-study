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

    @Id @GeneratedValue
    @Column(name = "post_image_id", nullable = false)
    private long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "FK_Image_Board"))
    private Post post_id;

    @Column(nullable = false)
    private String image_url;

    private Long order_num;
}
