package sungshin.sooon.dto;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import sungshin.sooon.model.Account;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @Email
    @NotNull
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{10,}$")
    @NotNull
    private String password;

    @NotNull
    private String nickname;
    private LocalDateTime registeredDateTime;

    public Account toAccount(PasswordEncoder passwordEncoder) {
        return Account.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .registeredDateTime(LocalDateTime.now())
                .build();
    }
}
