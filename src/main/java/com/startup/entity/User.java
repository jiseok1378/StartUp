package com.startup.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class User {

    @Id
    private String userId;

    private String password;

    private String email;

    @Column(name = "register_number", unique = true)
    private String registerNumber;


    @Column(length = 30)
    private String name;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Board> boardList = new ArrayList<>();

    public User addBoard(Board board){
        boardList.add(board);
        return this;
    }



}
