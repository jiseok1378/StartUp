package com.startup.service;


import com.startup.entity.MessageInfo;
import com.startup.entity.User;
import com.startup.repository.MessageInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageInfoService {
    private final MessageInfoRepository messageInfoRepository;

    public MessageInfo add(MessageInfo messageInfo){
        return messageInfoRepository.save(messageInfo);
    }

    public List<MessageInfo> findSendMessageInfo(User fromUser){
        return messageInfoRepository.findByFromUserId(fromUser.getUserId());
    }

}
