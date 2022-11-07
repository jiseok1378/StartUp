package com.startup.service;

import com.startup.dto.login.LoginDtoImpl;
import com.startup.dto.login.SignUpDtoImpl;
import com.startup.dto.login.inter.LoginDto;
import com.startup.dto.login.inter.SignUpDto;
import com.startup.security.jwt.JwtProvider;
import com.startup.service.inter.LoginService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LoginServiceImplTest  {

    @Autowired
    private LoginService loginService;

    @Autowired
    private JwtProvider jwtProvider;

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

        Assertions.assertThat(jwtProvider.validationToken(loginService.logIn(loginDto))).isEqualTo(true);

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