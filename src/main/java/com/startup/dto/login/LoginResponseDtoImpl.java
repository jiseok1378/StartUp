package com.startup.dto.login;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.startup.dto.login.inter.LoginResponse;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class LoginResponseDtoImpl implements LoginResponse {
    private String userName;
    private String refreshToken;
    private String accessToken;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expirationDate;
}
