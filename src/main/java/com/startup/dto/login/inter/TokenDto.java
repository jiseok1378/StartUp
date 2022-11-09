package com.startup.dto.login.inter;


import java.util.Date;

public interface TokenDto {
    String getAccessToken();
    String getRefreshToken();

    Date getExpirationDate(); // access token 의 expire date 전달
}
