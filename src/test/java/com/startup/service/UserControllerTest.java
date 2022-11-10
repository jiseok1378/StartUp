package com.startup.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.startup.dto.login.*;
import com.startup.dto.login.inter.LoginDto;
import com.startup.entity.User;
import com.startup.repository.UserRepository;
import com.startup.security.jwt.JwtProvider;
import com.startup.service.inter.UserService;
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

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ComponentScan(basePackages =
        {
                "com.startup.security.config",
                "com.startup.security.jwt"
        })
public class UserControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserRepository userRepository;

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

        System.out.println(userRepository.findByUserIdAndPassword(loginDto.getUserId(), loginDto.getPassword()).get().getUserId());;
        Assertions.assertThat(jwtProvider.validationToken(loginResponseDto.getAccessToken())).isEqualTo(true);

        Thread.sleep(1000);
        String reissuedAccessToken = mvc.perform(MockMvcRequestBuilders.post("/api/v1/token")
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .content(mapper.writeValueAsBytes(TokenReissueDto.builder().userId(loginDto.getUserId()).token(loginResponseDto.getAccessToken()).build())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String logoutResult = mvc.perform(MockMvcRequestBuilders.post("/api/v1/logout")
                        .header(JwtProvider.TOKEN_HEADER_KEY, reissuedAccessToken)
                        .content(loginDto.getUserId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertThat(mapper.readValue(logoutResult, Boolean.class)).isEqualTo(true);

        User afterLogout = userRepository.findByUserIdAndPassword(loginDto.getUserId(), loginDto.getPassword()).orElseThrow();

        Assertions.assertThat(afterLogout.getRefreshToken()).isEqualTo(null);
        String deleteUserResult = mvc.perform(MockMvcRequestBuilders.delete("/api/v1/user")
                .header(JwtProvider.TOKEN_HEADER_KEY, reissuedAccessToken)
                .content(loginDto.getUserId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertThat(mapper.readValue(deleteUserResult, Boolean.class)).isEqualTo(true);
    }
}