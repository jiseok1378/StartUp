package com.startup.service;
import com.startup.dto.login.inter.LoginDto;
import com.startup.dto.login.inter.SignUpDto;
import com.startup.entity.User;
import com.startup.repository.UserRepository;
import com.startup.security.jwt.JwtProvider;
import com.startup.service.inter.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    public String logIn(LoginDto loginDto) {
        Optional<User> _user = userRepository.findByUserIdAndPassword(loginDto.getUserId(), loginDto.getPassword());
        if(_user.isPresent()){
            User user = _user.get();
            List<String> roles = new ArrayList<>();
            roles.add("USER");
            return jwtProvider.createToken(user.getUserId(), roles);
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
            User user = new User(signUpDto);
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
