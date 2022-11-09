package com.startup.controller;

import com.startup.dto.login.LoginDtoImpl;
import com.startup.dto.login.SignUpDtoImpl;
import com.startup.dto.login.inter.LoginResponse;
import com.startup.dto.login.inter.SignUpDto;
import com.startup.service.inter.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginDtoImpl loginDto){
        return loginService.logIn(loginDto);
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody SignUpDtoImpl signUpDto){
        return loginService.signUp(signUpDto);
    }
}
