package com.startup.service;


import com.startup.entity.User;
import com.startup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    @Transactional
    public User add(User user){
        User result = userRepository.save(user);
        return result;
    }

    public Optional<User> findWithPassword(String userId, String password){
        return userRepository.findByUserIdAndPassword(userId, password);
    }


}
