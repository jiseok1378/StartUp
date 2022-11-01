package com.startup.service;


import com.startup.entity.Message;
import com.startup.entity.MessageInfo;
import com.startup.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public Message send(Message message){
        return messageRepository.save(message);
    }
    public List<Message> findMessages(MessageInfo messageInfo){
        return messageRepository.findByMessageInfoId(messageInfo.getId());
    }
}
