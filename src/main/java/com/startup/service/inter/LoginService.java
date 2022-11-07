package com.startup.service.inter;

import com.startup.dto.login.inter.LoginDto;
import com.startup.dto.login.inter.SignUpDto;

public interface LoginService {

    // LoginDTO를 받아 jwt String을 반환
    String logIn(LoginDto loginDto);
    void logOut();

    // 성공적으로 로그인되면 유저 이름을 반환
    String signUp(SignUpDto signUpDto);
    void deleteUser();
}
