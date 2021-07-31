package sungshin.sooon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sungshin.sooon.dto.LoginDto;
import sungshin.sooon.dto.AccountDto;
import sungshin.sooon.dto.TokenDto;
import sungshin.sooon.service.AccountService;
import sungshin.sooon.util.exception.Result;
import sungshin.sooon.util.exception.ResultCode;

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
    public ResponseEntity<Result<TokenDto>> login(@RequestBody LoginDto loginDto) {
        TokenDto data = accountService.login(loginDto);
        return Result.toResult(ResultCode.LOGIN_SUCCESS, data);
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<Result> signup(@RequestBody @Valid AccountDto accountDto) {
        accountService.signup(accountDto);
        return Result.toResult(ResultCode.SIGNUP_SUCCESS);
    }

    // 이메일 중복 체크
    @GetMapping("/check-email/{email}")
    public ResponseEntity<Result> checkEmail(@PathVariable @NotBlank @Email String email) {
        accountService.checkEmail(email);
        return Result.toResult(ResultCode.CHECK_EMAIL_SUCCESS);
    }

    // 닉네임 중복 체크
    @GetMapping("/check-nickname/{nickname}")
    public ResponseEntity<Result> checkNickname(@PathVariable @NotBlank String nickname) {
        accountService.checkNickname(nickname);
        return Result.toResult(ResultCode.CHECK_NICKNAME_SUCCESS);
    }
}
