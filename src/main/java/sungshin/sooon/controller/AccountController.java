package sungshin.sooon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sungshin.sooon.dto.LoginRequest;
import sungshin.sooon.dto.SignUpRequest;
import sungshin.sooon.dto.TokenDto;
import sungshin.sooon.service.AccountService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AccountController {

    private final AccountService accountService;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(accountService.login(loginRequest));
    }

    // 회원가입
    @PostMapping("/signup")
    public String signup(@RequestBody SignUpRequest signUpRequest) {
        accountService.signup(signUpRequest);

        return "redirect:/login";
    }

}
