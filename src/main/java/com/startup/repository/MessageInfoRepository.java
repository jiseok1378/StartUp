package com.startup.repository;


import com.startup.entity.MessageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageInfoRepository extends JpaRepository<MessageInfo, Integer> {

    List<MessageInfo> findByFromUserId(String fromUserId);
}
