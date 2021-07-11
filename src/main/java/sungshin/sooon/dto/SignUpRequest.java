package sungshin.sooon.dto;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import sungshin.sooon.model.Account;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    private Long id;
    private String email;
    private String password;
    private String nickname;

    public Account toAccount(PasswordEncoder passwordEncoder) {
        return Account.builder()
                .id(id)
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .build();
    }
}
