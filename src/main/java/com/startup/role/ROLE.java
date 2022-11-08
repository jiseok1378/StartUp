package com.startup.role;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ROLE {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");
    private final String role;
}
