package sungshin.sooon.model;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

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

    @Column(name = "email", unique = true, length = 20, nullable = false)
    @NotBlank(message = "Email should not be blank")
    @Email(message = "Please format your email")
    private String email;

    @Column(name = "password", length = 16, nullable = false)
    @NotBlank(message = "Password should not be blank")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{10,16}$",
            message = "10 or more characters - including English, numeric and special characters")
    private String password;

    @NotBlank(message = "Nickname should not be blank")
    @Column(name = "nickname")
    private String nickname;

}
