package com.startup.service;

import com.startup.entity.Board;
import com.startup.entity.User;
import com.startup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    @Transactional
    public User add(User user){
        User result = userRepository.save(user);
        return result;
    }

    @Transactional
    public User find(String key){
        return userRepository.findById(key).orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void remove(User user){
        userRepository.delete(user);
    }

}
