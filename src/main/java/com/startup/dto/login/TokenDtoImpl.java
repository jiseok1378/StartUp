package com.startup.dto.login;

import com.startup.dto.login.inter.TokenDto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDtoImpl implements TokenDto {
    private String accessToken;
    private String refreshToken;
}
