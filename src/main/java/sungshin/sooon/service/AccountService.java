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
import sungshin.sooon.dto.LoginDto;
import sungshin.sooon.dto.AccountDto;
import sungshin.sooon.dto.TokenDto;
import sungshin.sooon.domain.account.Account;
import sungshin.sooon.domain.account.RefreshToken;
import sungshin.sooon.domain.account.UserAccount;
import sungshin.sooon.domain.account.AccountRepository;
import sungshin.sooon.domain.account.RefreshTokenRepository;
import sungshin.sooon.util.SecurityUtil;
import sungshin.sooon.util.exception.DuplicateException;
import sungshin.sooon.util.exception.ResultCode;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;

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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username);

        if(account == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserAccount(account);
    }

    // 로그인
    @Transactional
    public TokenDto login(LoginDto loginDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginDto.toAuthentication();

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

    // 회원가입
    @Transactional
    public void signup(@Valid AccountDto accountDto) throws EntityExistsException {
        checkEmail(accountDto.getEmail());
        checkNickname(accountDto.getNickname());
        Account account = accountDto.toAccount(passwordEncoder);
        accountRepository.save(account);
    }

    // 이메일 중복 체크
    public void checkEmail(String email) {
        if (accountRepository.existsByEmail(email)) {
            throw new DuplicateException(ResultCode.EMAIL_DUPLICATION);
        }
    }

    // 닉네임 중복 체크
    public void checkNickname(String nickname) {
        if (accountRepository.existsByNickname(nickname)) {
            throw new DuplicateException(ResultCode.NICKNAME_DUPLICATION);
        }
    }
}
