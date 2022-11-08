package com.startup.dto.login.inter;

public interface SignUpDto {
    String getUserId();
    String getPassword();
    String getName();
    String getEmail();
    String getRegisterNumber();

    void setUserId(String userId);
    void setPassword(String password);
    void setName(String name);
    void setEmail(String email);
    void setRegisterNumber(String registerNumber);


}
