package com.startup.service;
import com.startup.dto.login.LoginResponseDtoImpl;
import com.startup.dto.login.TokenReissueDto;
import com.startup.dto.login.inter.LoginDto;
import com.startup.dto.login.inter.LoginResponse;
import com.startup.dto.login.inter.SignUpDto;
import com.startup.dto.login.inter.TokenDto;
import com.startup.entity.User;
import com.startup.repository.UserRepository;
import com.startup.role.ROLE;
import com.startup.security.jwt.JwtProvider;
import com.startup.service.inter.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public LoginResponse logIn(LoginDto loginDto) {
        Optional<User> _user = userRepository.findByUserIdAndPassword(loginDto.getUserId(), loginDto.getPassword());
        if(_user.isPresent()){
            User user = _user.get();
            TokenDto token = jwtProvider.createAccessAndRefreshToken(user.getUserId(), user.getRoles()); // jwt 생성
            user.setRefreshToken(token.getRefreshToken()); // refresh token 저장

            return LoginResponseDtoImpl.builder()
                    .accessToken(token.getAccessToken())
                    .expirationDate(token.getExpirationDate()) // access token의 expire Date 전달
                    .userName(user.getName())
                    .build();
        }
        else{
            throw new NoSuchElementException();
        }
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
    public String reissueToken(TokenReissueDto tokenReissueDto) {
        User user = userRepository.findById(tokenReissueDto.getUserId()).orElseThrow();
        TokenDto tokenDto = jwtProvider.checkValidationAndReissue(user, tokenReissueDto.getToken()).orElseThrow();
        return tokenDto.getAccessToken();
    }

    @Override
    public boolean deleteUser(String userId) {
        Optional<User> _user = userRepository.findById(userId);
        if(_user.isEmpty()) {
            System.out.println("계정을 삭제하려고 하였지만, "+userId+"에 해당하는 유저가 없습니다.");
            return false;
        }
        else{
            userRepository.delete(_user.get());
            return true;
        }
    }

    @Override
    public boolean checkUserDuplicate(String userId) {
        return userRepository.findById(userId).isEmpty();
    }

    @Override
    @Transactional
    public boolean logout(String userId) {
        Optional<User> _user = userRepository.findById(userId);
        if(_user.isEmpty()){
            System.out.println("로그아웃을 시도했지만, " + userId + "에 해당하는 유저가 없습니다.");
            return false;
        }
        else{
            User user = _user.get();
            user.setRefreshToken(null);
            return true;
        }
    }
}
