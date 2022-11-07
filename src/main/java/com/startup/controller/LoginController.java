package com.startup.controller;

import com.startup.dto.login.LoginDtoImpl;
import com.startup.service.inter.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String login(LoginDtoImpl loginDto){
        loginService.logIn(loginDto);
        return "test";
    }

}
