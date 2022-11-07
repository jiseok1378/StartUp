package com.startup.dto;

import com.startup.dto.inter.SignUpDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class SignUpDtoImpl implements SignUpDto {
    private String userId;
    private String password;
    private String email;
    private String registerNumber;
    private String name;
}