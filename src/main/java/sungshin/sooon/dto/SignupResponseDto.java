package sungshin.sooon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sungshin.sooon.domain.entity.Account;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupResponseDto {
    private Long id;
    private String email;
    private String nickname;

    public static SignupResponseDto of(Account account) {
        return SignupResponseDto
                .builder()
                .id(account.getId())
                .email(account.getEmail())
                .nickname(account.getNickname())
                .build();
    }
}
