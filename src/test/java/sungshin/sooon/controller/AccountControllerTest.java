package sungshin.sooon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import sungshin.sooon.dto.AccountDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입 Test")
    @WithMockUser
    void test_register() throws Exception {
        AccountDto dto = AccountDto.builder()
                .email("test@test.com")
                .password("test123!!!")
                .nickname("test")
                .build();

        mockMvc.perform(post("/api/v1/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print());
    }

    @Test
    @DisplayName("이메일 중복 체크 Test")
    @WithMockUser
    void test_checkEmail() throws Exception {
        mockMvc.perform(get("/api/v1/auth/checkEmail/test@test.com"))
                .andDo(print());
    }


    @Test
    @DisplayName("닉네 중복 체크 Test")
    @WithMockUser
    void test_checkNickname() throws Exception {
        mockMvc.perform(get("/api/v1/auth/checkNickname/test"))
                .andDo(print());
    }
}
