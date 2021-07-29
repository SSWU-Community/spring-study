package sungshin.sooon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import sungshin.sooon.config.HttpLogoutSuccessHandler;
import sungshin.sooon.config.JwtAccessDeniedHandler;
import sungshin.sooon.config.JwtAuthenticationEntryPoint;
import sungshin.sooon.config.TokenProvider;
import sungshin.sooon.dto.AccountResponseDto;
import sungshin.sooon.dto.SignupRequestDto;
import sungshin.sooon.service.AccountService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sungshin.util.ApiDocumentUtils.getDocumentRequest;
import static sungshin.util.ApiDocumentUtils.getDocumentResponse;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(controllers = AccountController.class)

/*(1) getDocumentRequest에 선언된 uri 와 동일 기능을 제공합니다. 아래의 우선순위로 적용됩니다.

@AutoConfigureRestDocs 에 uri 정보가 선언되어있으면 적용 없으면 2 단계로
        getDocumentRequest 에 uri 정보가 설정되어있으면 적용 없으면 3 단계로
        기본설정값 적용 http://localhost:8080*/
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    HttpLogoutSuccessHandler httpLogoutSuccessHandler;

    @MockBean
    JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @MockBean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    //(2) mocking을 하기위해 @MockBean 을 선언하였습니다.
    @MockBean // (2)
    private AccountService accountService;

    @Test
    void checkEmail() throws Exception {
        //given
        String email = "20181017@sungshin.ac.kr";
        given(accountService.checkEmail(email))
                .willReturn(true);

        //when
        ResultActions result = this.mockMvc.perform(
                get("/api/v1/auth/check-email").param("email", email)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("check-email",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("email").description("이메일")
                        )
                ));
    }

    @Test
    void checkNickname() throws Exception {
        //given
        String nickname = "chaeppy";
        given(accountService.checkNickname(nickname))
                .willReturn(true);

        //when
        ResultActions result = this.mockMvc.perform(
                get("/api/v1/auth/check-email").param("nickname", nickname)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("check-nickname",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("nickname").description("닉네임")
                        )
                ));
    }


    @Test
    void signup() throws Exception {
        //given
        given(accountService.signup(any(SignupRequestDto.class)))
                .willReturn(AccountResponseDto
                        .builder()
                        .id(1L)
                        .email("20181017@sungshin.ac.kr")
                        .nickname("chaeppy")
                        .build());

        Request request = new Request();
        request.email = "20181017@sungshin.ac.kr";
        request.nickname = "chaeppy";
        request.password = "asbsaaaced12!";

        //when
        ResultActions result = this.mockMvc.perform(
                post("/api/v1/auth/signup")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("check-nickname",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                        ),
                        responseFields(beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임")
                        )
                ));
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Request {
        String email;
        String nickname;
        String password;
    }
}


