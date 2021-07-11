package sungshin.sooon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sungshin.sooon.config.TokenProvider;
import sungshin.sooon.dto.LoginRequest;
import sungshin.sooon.dto.SignUpRequest;
import sungshin.sooon.dto.TokenDto;
import sungshin.sooon.model.Account;
import sungshin.sooon.model.RefreshToken;
import sungshin.sooon.model.UserAccount;
import sungshin.sooon.repository.AccountRepository;
import sungshin.sooon.repository.RefreshTokenRepository;
import sungshin.sooon.util.SecurityUtil;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    // 로그인한 유저 정보 반환 to @CurrentUser
    public Account getUserInfo() {
        return accountRepository.findByEmail(SecurityUtil.getUserName());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email);

        if(account == null) {
            throw new UsernameNotFoundException(email);
        }
        return new UserAccount(account);
    }

    // 로그인
    @Transactional
    public TokenDto login(LoginRequest loginRequest) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginRequest.toAuthentication();

        // 2. 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .email(authentication.getName())
                .tokenValue(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 5. 토큰 발급
        return tokenDto;
    }

    @Transactional
    public void signup(SignUpRequest signUpRequest) {
        Account account = signUpRequest.toAccount(passwordEncoder);
        Account searchAccount = accountRepository.findByEmail(account.getEmail());
        if (searchAccount == null) {
            accountRepository.save(searchAccount);
        }
    }
}
