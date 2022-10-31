package com.startup.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
public class Tag {
    @Id
    @GeneratedValue
    private int id;

    @Column(name="board_id")
    private int boardId;

    @ManyToOne
    @JoinColumn(name = "board_id", updatable = false, insertable = false)
    private Board board;

    private String contents;
}
