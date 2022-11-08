package com.startup.dto.login.inter;


public interface LoginDto {
    String getUserId();
    String getPassword();

    void setUserId(String userId);
    void setPassword(String password);
}
