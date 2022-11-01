package com.startup.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
public class Message {

    @Id
    private int id;

    @Column(name = "message_info_id")
    private int messageInfoId;

    @ManyToOne
    @JoinColumn(name = "message_info_id", insertable = false, updatable = false)
    private MessageInfo messageInfo;

    private String contents;

    @Builder
    public Message(int messageInfoId, String contents){
        this.messageInfoId = messageInfoId;
        this.contents = contents;
    }
}
