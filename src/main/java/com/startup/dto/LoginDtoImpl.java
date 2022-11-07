package com.startup.dto;

import com.startup.dto.inter.LoginDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class LoginDtoImpl implements LoginDto {
    private String userId;
    private String password;

}
