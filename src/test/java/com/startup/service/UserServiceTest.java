package com.startup.service;

import com.startup.entity.Board;
import com.startup.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @Test
    public void testAdd() {

        User user = User.builder()
                .userId("test")
                .name("test")
                .boardList(new ArrayList<>())
                .registerNumber("test")
                .build();

        Board newBoard1 =
                Board.builder()
//                        .userId(user.getUserId())
                        .contents("하하하")
                        .build();
        Board newBoard2 =
                Board.builder()
//                        .userId(user.getUserId())
                        .contents("하하하")
                        .build();
        user.addBoard(newBoard1);
        user.addBoard(newBoard2);

        userService.add(user);
        User findUser = userService.find("test");
        Assertions.assertThat(findUser.getName()).isEqualTo("test");


        Board findBoard = boardService.find(1);
        Assertions.assertThat(findBoard.getContents()).isEqualTo("하하하");
//        Assertions.assertThat(findBoard.getUserId()).isEqualTo("test");


//        userService.remove(findUser);
    }

}