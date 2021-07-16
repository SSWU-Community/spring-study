package sungshin.sooon.domain.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    /*
    기본 키 생성을 데이터베이스에 위임
    즉, id 값을 null로 하면 DB가 알아서 AUTO_INCREMENT 해준다
    출처: https://gmlwjd9405.github.io/2019/08/12/primary-key-mapping.html
     */

    /*
     Spring Boot는 Hibernate의 id 생성 전략을 그대로 따라갈지 말지를 결정하는 useNewIdGeneratorMappings 설정이 있다.
     키 생성전략을 사용하려면 이 속성을 반드시 true로 두어야한다.
        1.5에선 기본값이 false, 2.0부터는 true
        Hibernate 5.0부터 MySQL의 AUTO는 IDENTITY가 아닌 TABLE을 기본 시퀀스 전략으로 선택된다.
        즉, 1.5에선 Hibernate 5를 쓰더라도 AUTO를 따라가지 않기 때문에 IDENTITY가 선택
        2.0에선 true이므로 Hibernate 5를 그대로 따라가기 때문에 TABLE이 선택

    해결책은 크게 2가지
        1. application.properties/yml의 use-new-id-generator-mappings을 false로 설정한다
        2. @GeneratedValue의 전략을 GenerationType.IDENTITY로 지정한다
            둘 중 어느것을 하더라도 아래처럼 의도했던대로 잘 수행되는 것을 확인할 수 있습니다.

    출처: https://jojoldu.tistory.com/295
    */

    /*
    IDENTITY 전략은 데이터를 데이터베이스에 INSERT한 후에 기본 키 값을 조회할 수 있기 때문에 엔티티에 식별자 값을 할당하려면 JPA는 추가로 데이터베이스를 조회해야 한다.
    영속 상태의 엔티티는 반드시 식별자가 있어야 하기 때문에, IDENTITY 전략을 사용하게 되면 em.persist()가 호출되는 즉시, INSERT SQL이 데이터베이스에 전달된다. 따라서 이 전략은 트랜잭션을 지원하는 쓰기 지연이 동작하지 않는다.
    출처: https://leejaedoo.github.io/entity_mapping/
     */

    /*
    nullable(DDL)DDL 생성 시에 not null 제약 조건을 추가한다.
    단지 DDL을 자동 생성할때만 사용되고 JPA의 실행로직에는 영향을 주지 않는다.
    따라서 스키마 자동 생성기능을 사용하지 않고 직접 DDL을 만든다면 사용할 이유가 없다.
    그래도 이 기능을 사용하면 어플리케이션 개발자가 엔티티만 보고도 손쉽게 다양한 제약조건을 파악할 수 있는 장점이있다.
     */
    @Id
    @Column(name = "post_id", nullable = false)
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;          // 기본키

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;


    /*
    * 테이블의 외래키 컬럼을 그대로 사용하지 않은이유:
    *   테이블은 외래키를 사용해서 연관된 테이블을 찾음,
    *   객체에서 외래키를 그대로 갖고 있으면 객체에는 조인이라는 기능이 없어서 post.getAccount()처럼 객체 그래프를 탐색(객체 연관관계를 사용한 조회)할 수 없고, 객체의 특성도 살릴 수 없다.
    * JPA는 객체의 참조와 테이블의 외래키를 매핑
    *   => 객체에서는 참조를 사용하고 테이블에서는 외래키를 사용할 수 있도록 한다.
    * */
    @ManyToOne(fetch = FetchType.LAZY) //주로 익명으로 사용되기 때문에 지연로딩. 익명이 아닐경우에만 조회가 필요함. account.getId() 호출시에는 프록시 객체 초기화가 일어나지 않는다.
    @JoinColumn(name = "account_id")
    private Account account;

    /*
       연관관계 편의 메소드
        한번에 양방향 관계를 설정하는 메소드
            양뱡향 연관관계는 결국 양쪽 다 신경써야한다. 각각 호출하다보면 실수로 둘 중 하나만 호출해서 양방향이 깨질 수도 있다.
            Post의 setAccount를 수정해 두 코드를 하나인 것 처럼 사용하는 것이 안전하다.
            연관관계 편의 메소드 작성 시, 무한루프에 빠지지 않도록 주의해야 한다.

        기존 member1과 teamA간의 관계가 아직 남아있기 때문에 teamA에서 여전히 member1이 조회가 되버린다.
            연관관계 편의 메소드에 기존 teamA -> member1 관계를 제거 후 변경하는 코드를 적용해야 한다.
            기존 teamA -> member1 관계를 제거하지 않아도 데이터베이스의 외래 키 참조를 teamA에서 teamB로 변경하는 것에는 문제가 없다.
            다만 기존 관계가 끊어진 상태가 아니기 때문에 teamA의 getMembers() 적용 시 여전히 member1이 조회된다는 것이 문제일 뿐이다.
     */

    public void setAccount(Account account) {
        if (this.account != null) {
            this.account.getPosts().remove(this);
        }

        this.account = account;

        // 편의 메소드는 한 곳에만 작성하거나 양쪽 다 작성할 수 있다. 양쪽 엔티티 둘다 작성한다면 무한루프에 빠지지 않도록 체크
        if (!account.getPosts().contains(this)) {
            account.getPosts().add(this);
        }
    }

    /*
    FetchType default 값
        @OneToMany, @ManyToMany : 지연 로딩(FetchType.LAZY)
        @ManyToOne, @OneToOne : 즉시 로딩(FetchType.EAGER)

        postImages는 post와 함께 자주 쓰이긴하지만 실제로 게시글을 눌러서 조회했을때만 필요하다. 기본값인 지연로딩을 사용하는게 좋을 듯?
    */
    @OneToMany(mappedBy = "post")
    private List<PostImages> postImages = new ArrayList<>();

    // 편의 메소드는 한 곳에만 작성하거나 양쪽 다 작성할 수 있다. 양쪽 엔티티 둘다 작성한다면 무한루프에 빠지지 않도록 체크
    public void addPostImage(PostImages postImage) {
        this.postImages.add(postImage);

        if (postImage.getPost() != this) { //  무한루프에 빠지지 않도록 체크
            postImage.setPost(this);
        }
    }

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean isAnonymous = true;

    /*
    @Temporal(TemporalType.TIMESTAMP)

    날짜 타입(java.util.Date, java.util.Calendar)을 매핑할 때 사용한다. 해당 어노테이션을 생략하게 되면 자바의 Date와 유사한 timestamp로 정의된다.
    하지만 timestamp대신 datetime을 예약어로 사용하는 데이터베이스도 있는데 데이터베이스 방언 덕분에 어플리케이션 코드는 변경하지 않아도 된다.
    자바8에서 지원하는 LocalDate, LocalDateTime을 사용할때는 생략 가능(하이버네이트 지원)
     */
    @Column
    private LocalDateTime createdAt;

    /*
    @Transient
    데이터베이스와 상관없이 개발자가 필요에 의해 메모리에서만 사용하고 싶은 필드를 지정하고 싶을때 사용한다.
    데이터베이스에 저장, 조회 되지 않는다.
     */
}
