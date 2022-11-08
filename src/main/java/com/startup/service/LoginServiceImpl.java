package com.startup.service;
import com.startup.dto.login.inter.LoginDto;
import com.startup.dto.login.inter.SignUpDto;
import com.startup.dto.login.inter.TokenDto;
import com.startup.entity.User;
import com.startup.repository.UserRepository;
import com.startup.role.ROLE;
import com.startup.security.jwt.JwtProvider;
import com.startup.service.inter.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public String logIn(LoginDto loginDto) {
        Optional<User> _user = userRepository.findByUserIdAndPassword(loginDto.getUserId(), loginDto.getPassword());
        if(_user.isPresent()){
            User user = _user.get();
            TokenDto token = jwtProvider.createToken(user.getUserId(), user.getRoles()); // jwt 생성
            user.setRefreshToken(token.getRefreshToken()); // refresh token 저장

            return token.getAccessToken(); // 클라이언트 쿠키에서 저장될 access token만 전달
        }
        else{
            throw new NoSuchElementException();
        }
    }

    @Override
    public void logOut() {
    }

    @Override
    public String signUp(SignUpDto signUpDto) {
        Optional<User> _user = userRepository.findById(signUpDto.getUserId());
        if(_user.isEmpty()){
            User user = new User(signUpDto, Collections.singletonList(ROLE.USER));
            return userRepository.save(user).getName();
        }
        else{
            throw new NoSuchElementException();
        }
    }

    @Override
    public void deleteUser() {

    }
}
