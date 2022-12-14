package com.startup.entity;

import com.startup.entity.key.TagKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@IdClass(TagKey.class)
public class Tag {

    @Id
    @GeneratedValue
    private int id;

    @Id
    @Column(name="board_id")
    private int boardId;

    @ManyToOne
    @JoinColumn(name = "board_id", updatable = false, insertable = false)
    private Board board;

    private String contents;

    @Builder
    public Tag(int boardId, String contents){
        this.boardId = boardId;
        this.contents = contents;
    }
}
