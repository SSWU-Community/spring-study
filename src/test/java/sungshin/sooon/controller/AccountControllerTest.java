package sungshin.sooon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import sungshin.sooon.dto.LoginRequestDto;
import sungshin.sooon.dto.RegisterRequestDto;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@SpringBootTest
public class AccountControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void 회원가입_POST() throws Exception{
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
            .email("test@sungshin.ac.kr")
            .nickname("test11")
            .password("sujeongmining2021!")
            .build();

        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(objectMapper.writeValueAsString(registerRequestDto)));
    }

    @Test
    @WithMockUser
    void check_email() throws Exception{
        mockMvc.perform(get("/api/v1/auth/checkEmail")
            .param("email","fltcy2039@sswu.ac.kr"));
    }

    @Test
    @WithMockUser
    void 로그인() throws Exception{
        LoginRequestDto loginRequest = LoginRequestDto.builder()
                .email("test@sungshin.ac.kr")
                .password("sujeongmining2021!")
                .build();

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(objectMapper.writeValueAsString(loginRequest)))
                .andDo(print());
    }

    @Test
    @WithMockUser
    void 로그아웃() throws Exception{
        mockMvc.perform(get("/api/v1/auth/logout"));
    }



}
