package com.startup.dto.login.inter;

import java.util.Date;

public interface LoginResponse {
    String getUserName();
    String getAccessToken();

    Date getExpirationDate(); // access token의 expire date 전달
}
