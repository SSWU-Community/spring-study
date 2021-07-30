package sungshin.sooon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import sungshin.sooon.dto.LoginRequestDto;
import sungshin.sooon.dto.RegisterRequestDto;
import sungshin.sooon.dto.TokenDto;
import sungshin.sooon.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AccountController {

    private final AccountService accountService;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequestDto dto) {
        return ResponseEntity.ok(accountService.login(dto));
    }

    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request,response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }

    //회원가입
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDto registerRequestDto) {
        return new ResponseEntity(accountService.register(registerRequestDto), HttpStatus.CREATED);
    }


    //이메일 중복확인
    @GetMapping("/checkEmail")
    public ResponseEntity checkEmail(@RequestParam(value = "email", defaultValue = "", required = false) String email) {
        return ResponseEntity.ok(accountService.checkEmail(email));
    }

    //이메일 중복확인
    @GetMapping("/checkNickname")
    public ResponseEntity checkNickname(@RequestParam(value = "nickname", defaultValue = "", required = false) String nickname) {
        return ResponseEntity.ok(accountService.checkNickname(nickname));
    }
}
