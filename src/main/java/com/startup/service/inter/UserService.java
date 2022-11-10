package com.startup.service.inter;

import com.startup.dto.login.TokenReissueDto;
import com.startup.dto.login.inter.LoginDto;
import com.startup.dto.login.inter.LoginResponse;
import com.startup.dto.login.inter.SignUpDto;

public interface UserService {

    // LoginDTO를 받아 jwt String을 반환
    LoginResponse logIn(LoginDto loginDto);

    // Access 토큰을 반환
    String reissueToken(TokenReissueDto tokenReissueDto);
    // 성공적으로 로그인되면 유저 이름을 반환
    String signUp(SignUpDto signUpDto);
    // 성공적으로 삭제하면 true 예외의 경우엔 false
    boolean deleteUser(String userId);

    boolean logout(String userId);
}
