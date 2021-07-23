package sungshin.sooon.model;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

public class PostImages {

    @Id @GeneratedValue
    private long post_images_id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "FK_Image_Board"))
    private Post post_id;

    @Column(nullable = false)
    private String image_url;

    private Long order_num;
}
