package com.startup.dto.login;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.startup.dto.login.inter.TokenDto;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDtoImpl implements TokenDto {
    private String accessToken;
    private String refreshToken;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    private Date expirationDate;
}
