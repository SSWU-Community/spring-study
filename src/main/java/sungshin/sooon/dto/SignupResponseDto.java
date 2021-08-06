package sungshin.sooon.dto;

import lombok.*;
import sungshin.sooon.model.Account;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class SignupResponseDto {

    private Long id;
    private String email;
    private String nickname;

    public static SignupResponseDto from(Account account) {
        return SignupResponseDto.builder()
                .id(account.getId())
                .email(account.getEmail())
                .nickname(account.getNickname())
                .build();
    }
}