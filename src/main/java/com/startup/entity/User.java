package com.startup.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.startup.dto.login.inter.SignUpDto;
import com.startup.dto.user.UserDto;
import com.startup.role.ROLE;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User implements UserDetails {

    @Id
    private String userId;

    private String password;

    private String email;

    @Column(name = "register_number", unique = true)
    private String registerNumber;


    @Column(length = 30)
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Board> boardList = new ArrayList<>();

    @Setter
    private String refreshToken;

    @Builder
    public User(String userId, String password, String email, String registerNumber, String name, String refreshToken){
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.registerNumber = registerNumber;
        this.name = name;
        this.refreshToken = refreshToken;
    }

    public User(UserDto userDto){
        this.userId = userDto.getUserId();
        this.password = userDto.getPassword();
        this.email = userDto.getEmail();
        this.registerNumber = userDto.getRegisterNumber();
        this.name = userDto.getName();
        this.roles = userDto.getRoles();
        this.refreshToken = userDto.getRefreshToken();
    }
    public User(SignUpDto signUpDto, List<ROLE> roles){
        this.userId = signUpDto.getUserId();
        this.password = signUpDto.getPassword();
        this.email = signUpDto.getEmail();
        this.registerNumber = signUpDto.getRegisterNumber();
        this.name = signUpDto.getName();
        this.roles = roles.stream().map(ROLE::getRole).collect(Collectors.toList());
    }
    @ElementCollection
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.name;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
