package sungshin.sooon.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_comment_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY) //주로 익명이므로
    @JoinColumn(name = "account_id")
    private Account account;

    @Column
    private long orderNum; //익명1, 익명2 구분에 사용

    @Column(nullable = false, columnDefinition = "boolean default true")
    @Builder.Default
    private boolean isAnonymous = true;

    @Column
    private LocalDateTime createdAt;
}
