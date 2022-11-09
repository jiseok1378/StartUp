package com.startup.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.startup.dto.login.LoginDtoImpl;
import com.startup.dto.login.LoginResponseDtoImpl;
import com.startup.dto.login.SignUpDtoImpl;
import com.startup.dto.login.inter.LoginDto;
import com.startup.dto.login.inter.LoginResponse;
import com.startup.dto.login.inter.SignUpDto;
import com.startup.entity.User;
import com.startup.security.jwt.JwtProvider;
import com.startup.service.inter.LoginService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ComponentScan(basePackages =
        {
                "com.startup.security.config",
                "com.startup.security.jwt"
        })
public class LoginServiceImplTest  {

    @Autowired
    private LoginService loginService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;
    @Test
    public void controllerTest() throws Exception {;

        SignUpDtoImpl signUpDto = SignUpDtoImpl.builder()
                .email("email@xxxx.com")
                .name("startup")
                .password("1234")
                .userId("startup")
                .registerNumber("111111-1111111")
                .build();

        MvcResult signUpResult = mvc.perform(MockMvcRequestBuilders.post("/api/v1/signup")
                        .content(mapper.writeValueAsString(signUpDto))
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String resBodyOfSignUp = signUpResult.getResponse().getContentAsString();

        Assertions.assertThat(resBodyOfSignUp).isEqualTo(signUpDto.getName());

        LoginDto loginDto = LoginDtoImpl.builder()
                .password(signUpDto.getPassword())
                .userId(signUpDto.getUserId())
                .build();


        String resBodyOfLogin = mvc.perform(MockMvcRequestBuilders.post("/api/v1/login")
                        .content(mapper.writeValueAsString(loginDto))
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        LoginResponseDtoImpl loginResponseDto = mapper.readValue(resBodyOfLogin, LoginResponseDtoImpl.class);

        Assertions.assertThat(jwtProvider.validationToken(loginResponseDto.getAccessToken())).isEqualTo(true);
    }
    @Test
    public void testLogIn() {
        SignUpDto signUpDto = SignUpDtoImpl.builder()
                .email("abc2")
                .name("abc2")
                .userId("abc2")
                .password("abc2")
                .registerNumber("abc2")
                .build();

        Assertions.assertThat(loginService.signUp(signUpDto)).isEqualTo("abc2");

        LoginDto loginDto = LoginDtoImpl.builder()
                .userId("abc2")
                .password("abc2")
                .build();

        LoginResponse ret = loginService.logIn(loginDto);

        User user = userService.findWithPassword(loginDto.getUserId(), loginDto.getPassword()).orElseThrow();

        Assertions.assertThat(jwtProvider.isAccessTokenValid(ret.getAccessToken(), user.getRefreshToken())).isEqualTo(true);
    }

    public void testLogOut() {
    }

    @Test
    public void testSignUp() {
        SignUpDto signUpDto = SignUpDtoImpl.builder()
                .email("abc")
                .name("abc")
                .userId("abc")
                .password("abc")
                .registerNumber("abc")
                .build();
        Assertions.assertThat(loginService.signUp(signUpDto)).isEqualTo("abc");
    }

    public void testDeleteUser() {
    }
}