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

    @Builder
    public User(String userId, String password, String email, String registerNumber, String name){
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.registerNumber = registerNumber;
        this.name = name;
    }


}
