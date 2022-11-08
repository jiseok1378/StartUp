package com.startup.dto.login.inter;


public interface TokenDto {
    String getAccessToken();
    String getRefreshToken();
    void setRefreshToken(String accessToken);
    void setAccessToken(String refreshToken);
}
