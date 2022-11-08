package com.startup.dto.login;

import com.startup.dto.login.inter.LoginDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LoginDtoImpl implements LoginDto {
    private String userId;
    private String password;
}
