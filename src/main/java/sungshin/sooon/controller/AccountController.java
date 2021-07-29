package sungshin.sooon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sungshin.sooon.dto.LoginRequestDto;
import sungshin.sooon.dto.SignupRequestDto;
import sungshin.sooon.dto.TokenDto;
import sungshin.sooon.service.AccountService;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
    public ResponseEntity signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        accountService.signup(signupRequestDto);
        return ResponseEntity.ok("회원가입 성공");
    }

    // 이메일 중복확인
    @GetMapping("/check-email")
    public ResponseEntity checkEmail(@RequestParam @Email @NotBlank String email) {
        accountService.checkEmail(email);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 닉네임 중복확인
    @GetMapping("/check-nickname")
    public ResponseEntity checkNickname(@RequestParam String nickname) {
        boolean check = accountService.checkNickname(nickname);
        return new ResponseEntity(HttpStatus.OK);
    }

}
