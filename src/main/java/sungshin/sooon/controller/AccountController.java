package sungshin.sooon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sungshin.sooon.dto.LoginRequestDto;
import sungshin.sooon.dto.SignUpRequestDto;
import sungshin.sooon.dto.TokenDto;
import sungshin.sooon.service.AccountService;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.lang.reflect.Member;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AccountController {

    private final AccountService accountService;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(accountService.login(loginRequestDto));
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
        accountService.signup(signUpRequestDto);
        return ResponseEntity.ok("회원가입 성공");
    }

    // 이메일 중복확인
    @GetMapping("/checkEmail")
    public ResponseEntity checkEmail(@RequestParam @Email @NotBlank String email) {
        boolean check = accountService.checkEmail(email);
        if(check) {
            return ResponseEntity.badRequest().body("이미 사용중인 이메일입니다.");
        } else {
            return ResponseEntity.ok("사용 가능한 이메일입니다.");
        }
    }

    // 닉네임 중복확인
    @GetMapping("/checkNickname")
    public ResponseEntity checkNickname(@RequestParam String nickname) {
        boolean check = accountService.checkNickname(nickname);
        if(check) {
            return ResponseEntity.badRequest().body("이미 사용중인 닉네임입니다.");
        } else {
            return ResponseEntity.ok("사용 가능한 닉네임입니다.");
        }
    }

}
