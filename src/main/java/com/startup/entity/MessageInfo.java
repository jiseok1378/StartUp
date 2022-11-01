package com.startup.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
public class MessageInfo {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "from_user_id")
    private String fromUserId;

    @Column(name = "to_user_id")
    private String toUserId;

    @ManyToOne
    @JoinColumn(name = "to_user_id", insertable = false, updatable = false)
    private User toUser;

    @ManyToOne
    @JoinColumn(name = "from_user_id", insertable = false, updatable = false)
    private User fromUser;

    @OneToMany(mappedBy = "messageInfo")
    List<Message> messageList = new ArrayList<>();

    @Builder
    public MessageInfo(String fromUserId, String toUserId){
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }

}
