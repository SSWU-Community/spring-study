package sungshin.sooon.dto;

import lombok.*;
import sungshin.sooon.domain.entity.Account;

@Data
@Builder
public class AccountResponseDto{
    private Long id;
    private String email;
    private String nickname;
    private TokenDto token;

    public static AccountResponseDto of(Account account) {
        return AccountResponseDto
                .builder()
                .id(account.getId())
                .email(account.getEmail())
                .nickname(account.getNickname())
                .build();
    }
}
