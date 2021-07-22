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
import sungshin.sooon.domain.entity.Account;
import sungshin.sooon.domain.entity.RefreshToken;
import sungshin.sooon.domain.entity.UserAccount;
import sungshin.sooon.domain.repository.AccountRepository;
import sungshin.sooon.domain.repository.RefreshTokenRepository;
import sungshin.sooon.dto.LoginRequestDto;
import sungshin.sooon.dto.SignupRequestDto;
import sungshin.sooon.dto.SignupResponseDto;
import sungshin.sooon.dto.TokenDto;
import sungshin.sooon.exception.AlreadyExistsException;
import sungshin.sooon.util.SecurityUtil;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    // 로그인한 유저 정보 반환 to @CurrentUser
    @Transactional(readOnly = true)
    public Account getUserInfo() {
        return accountRepository.findByEmail(SecurityUtil.getCurrentUserName());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username);

        if (account == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserAccount(account);
    }

    //이메일 중복확인
    @Transactional
    public boolean checkEmail(String email) {
        if (accountRepository.existsByEmail(email)) {
            throw new AlreadyExistsException("이미 존재하는 이메일 입니다.");
        }
        return true;
    }

    //이메일 중복확인
    @Transactional
    public boolean checkNickname(String nickname) {
        //이 처리를 서비스단에서? 컨트롤러 단에서?
        if (accountRepository.existsByNickname(nickname)) {
            throw new AlreadyExistsException("이미 존재하는 이메일 입니다.");
        }
        return true;
    }

    //회원가입
    @Transactional
    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        if (accountRepository.existsByEmail(signupRequestDto.getEmail())) {
            throw new AlreadyExistsException("이미 가입되어 있는 유저입니다");
        }

        Account member = signupRequestDto.toAccount(passwordEncoder);
        return SignupResponseDto.of(accountRepository.save(member));
    }

    // 로그인
    @Transactional
    public TokenDto login(LoginRequestDto loginRequestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginRequestDto.toAuthentication();

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
}
