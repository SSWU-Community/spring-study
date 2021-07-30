package sungshin.sooon.domain.account;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Getter
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Account {
    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "account_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String email;

    @NotBlank
    @Length(min=10)
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private LocalDateTime registeredDateTime;

    // 인증 여부
//    private boolean isConfirmed;

    /**
     *
     * 기타 추가할 컬럼(필드)들 추가해주시면 됩니다~
     *
     */
}
