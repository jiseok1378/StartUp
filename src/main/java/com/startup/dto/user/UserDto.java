package com.startup.dto.user;

import com.startup.entity.User;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserDto {
    private String userId;
    private String password;
    private String email;
    private String registerNumber;
    private String name;
    private List<String> roles;
    private String refreshToken;
    public UserDto(User user){
        this.userId = user.getUserId();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.registerNumber = user.getRegisterNumber();
        this.name = user.getName();
        this.roles = user.getRoles();
        this.refreshToken = user.getRefreshToken();
    }
}
