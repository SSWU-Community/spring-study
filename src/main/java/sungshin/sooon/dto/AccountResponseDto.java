package sungshin.sooon.dto;

import lombok.Builder;
import lombok.Data;
import sungshin.sooon.domain.account.Account;

@Data
@Builder
public class AccountResponseDto {
    private Long id;
    private String nickname;
    private String email;
    private TokenDto tokenDto;

    public void setTokenDto(TokenDto tokenDto){
        this.tokenDto = tokenDto;
    }

    public static AccountResponseDto of(Account account){
        return AccountResponseDto.builder()
                .id(account.getId())
                .nickname(account.getNickname())
                .email(account.getEmail())
                .build();
    }
}
