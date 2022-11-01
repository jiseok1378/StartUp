package com.startup.entity;

import com.startup.entity.key.LikeKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@IdClass(LikeKey.class)
public class UserLike implements Serializable {
    @Id
    @Column(name="board_id")
    private int boardId;

    @ManyToOne
    @JoinColumn(name = "board_id", updatable = false, insertable = false)
    private Board board;

    @Id
    @Column(name = "user_id")
    private String userId;

    @OneToOne
    @JoinColumn(name = "user_id", updatable = false, insertable = false)
    private User user;

    @Builder
    public UserLike(int boardId, String userId){
        this.boardId = boardId;
        this.userId = userId;
    }
}
