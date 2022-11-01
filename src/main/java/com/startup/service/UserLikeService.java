package com.startup.service;

import com.startup.entity.UserLike;
import com.startup.entity.key.LikeKey;
import com.startup.repository.UserLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserLikeService {
    private final UserLikeRepository likeRepository;

    public UserLike addUserLikeToBoard(UserLike userLike){
        return likeRepository.save(userLike);
    }
    public UserLike findUserLike(LikeKey key){
        return likeRepository.findById(key).orElseThrow(NoSuchElementException::new);
    }
}
