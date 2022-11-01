package com.startup.repository;

import com.startup.entity.UserLike;
import com.startup.entity.key.LikeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLikeRepository extends JpaRepository<UserLike, LikeKey> {
}
