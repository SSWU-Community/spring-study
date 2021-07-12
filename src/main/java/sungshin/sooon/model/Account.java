package sungshin.sooon.model;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter @Setter
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Account {

    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(unique = true, length = 20, nullable = false)
    private String email;

    @Column(length = 16, nullable = false)
    private String password;

    private String nickname;

}
