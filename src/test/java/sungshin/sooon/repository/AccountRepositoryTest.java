package sungshin.sooon.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sungshin.sooon.domain.account.Account;
import sungshin.sooon.domain.account.AccountRepository;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;

    @AfterEach
    public void cleanup(){
        accountRepository.deleteAll();
    }

    @Test
    public void 조회(){
        List<Account> accountList = accountRepository.findAll();
        System.out.println("size :" + accountList.size());
    }

    @Test
    public void 회원가입(){
        String email = "sujeong@sswu.ac.kr";
        String pwd = "sujeongmining2021!";
        String nickName = "sio2";
        LocalDateTime registerTime = LocalDateTime.now();

        accountRepository.save(Account.builder()
                .email(email)
                .password(pwd)
                .nickname(nickName)
                .registeredDateTime(registerTime)
                .build());

        List<Account> accountList = accountRepository.findAll();
        Account account = accountList.get(0);
        System.out.println("here!"+account.getNickname()+" "+account.getPassword());
    }


}
