package com.startup.controller;

import com.startup.dto.login.LoginDtoImpl;
import com.startup.dto.login.SignUpDtoImpl;
import com.startup.dto.login.TokenReissueDto;
import com.startup.dto.login.inter.LoginResponse;
import com.startup.service.inter.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @PostMapping("/login") // 보안을 위해 Post 맵핑을 사용하였다. 맞는지는 잘 모르겠다.
    public LoginResponse login(@RequestBody LoginDtoImpl loginDto){
        return userService.logIn(loginDto);
    }

    @PostMapping("/signup")// 보안을 위해 Post 맵핑을 사용하였다. 맞는지는 잘 모르겠다.
    public String signUp(@RequestBody SignUpDtoImpl signUpDto){
        return userService.signUp(signUpDto);
    }

    @GetMapping("/user/duplicate")
    public boolean checkDuplication(@RequestParam("userId") String userId){
        return userService.checkUserDuplicate(userId);
    }

    @PostMapping("/token")// 보안을 위해 Post 맵핑을 사용하였다. 맞는지는 잘 모르겠다.
    public String reissueToken(@RequestBody TokenReissueDto tokenReissueDto){
        return userService.reissueToken(tokenReissueDto);
    }
    @PostMapping("/logout")
    public boolean logout(@RequestBody String userId){
        return userService.logout(userId);
    }


    @DeleteMapping("/user")
    public boolean deleteUser(@RequestBody String userId){
        return userService.deleteUser(userId);
    }
}
