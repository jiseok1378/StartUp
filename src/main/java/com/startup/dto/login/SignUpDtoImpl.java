package com.startup.dto.login;

import com.startup.dto.login.inter.SignUpDto;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SignUpDtoImpl implements SignUpDto {
    private String userId;
    private String password;
    private String email;
    private String registerNumber;
    private String name;
}
