package sungshin.sooon.dto;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import sungshin.sooon.model.Account;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {

    @NotBlank(message = "Email should not be blank")
    @Email(message = "Please format your email")
    private String email;

    @NotBlank(message = "Password should not be blank")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{10,16}$",
            message = "10 or more characters - including English, numeric and special characters")
    private String password;

    @NotBlank(message = "Nickname should not be blank")
    private String nickname;

    private LocalDateTime registeredDateTime;

    public Account toAccount(PasswordEncoder passwordEncoder) {
        return Account.builder()
                .email(this.email)
                .password(passwordEncoder.encode(this.password))
                .nickname(this.nickname)
                .registeredDateTime(LocalDateTime.now())
                .build();
    }
}
