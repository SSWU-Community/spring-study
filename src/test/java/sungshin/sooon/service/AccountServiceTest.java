package sungshin.sooon.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sungshin.sooon.dto.LoginRequestDto;
import sungshin.sooon.dto.RegisterRequestDto;
import sungshin.sooon.dto.TokenDto;
import sungshin.sooon.domain.account.AccountRepository;

@SpringBootTest
public class AccountServiceTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @AfterEach
    public void cleanup(){
        accountRepository.deleteAll();
    }

    @Test
    public void register(){
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .email("test@sungshin.ac.kr")
                .nickname("test11")
                .password("sujeongmining2021!")
                .build();

        accountService.register(registerRequestDto);

        if(accountService.checkEmail("fltcy2039@sswu.ac.kk")){
            System.out.println("있음");
        }else{
            System.out.println("없음");
        }

        if(accountService.checkNickname("sujeong")){
            System.out.println("닉네임 있음");
        }else{
            System.out.println("닉네임 없음");
        }
    }

    @Test
    public Boolean checkEmail(){
        System.out.println(accountRepository.existsByEmail("fltcy2039@sswu.ac.kr"));
        System.out.println(accountService.checkEmail("fltcy2039@sswu.ac.k4"));
        return accountService.checkEmail("fltcy2039@sswu.ac.r");
    }

    @Test
    public TokenDto 로그인() {
        LoginRequestDto loginRequest = LoginRequestDto.builder()
                .email("test@sungshin.ac.kr")
                .password("sujeongmining2021")
                .build();

        return accountService.login(loginRequest);
    }
}
