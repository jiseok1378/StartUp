package com.startup.role;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ROLE {
    USER("USER"),
    ADMIN("ADMIN");
    private final String role;
}
