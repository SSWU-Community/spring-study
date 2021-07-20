package sungshin.sooon.model;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@EqualsAndHashCode(of = "account_id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Account {

    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    private Long account_id;

    @Column(unique = true, length = 20, nullable = false)
    private String email;

    @Column(length = 16, nullable = false)
    private String password;

    private String nickname;

    private LocalDateTime registeredDateTime;

}
