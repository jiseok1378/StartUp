package com.startup.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Board {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "user_id")
    private String userId;

    @Builder
    public Board(String userId, String contents){
        this.userId = userId;
        this.contents = contents;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;


    private String contents;

    @OneToMany(mappedBy = "board")
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<UserLike> likes = new ArrayList<>();
}
