package sungshin.sooon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequestDto {
    @NotBlank(message = "email is required")
    @Email
    private String email;

    @NotBlank(message = "password is required")
    private String password;

    //로그인시 비밀번호 비교를 위해 호출
    //encode 필요 없음
    //DaoAuthenticationProvider에서 DB 에 있는 값은 암호화된 값이고 사용자가 입력한 값은 raw 값이지만 passwordEncoder 가 알아서 비교해줌
    //미인증 상태 반환
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
