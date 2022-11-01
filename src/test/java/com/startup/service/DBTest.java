package com.startup.service;

import com.startup.entity.*;
import com.startup.entity.key.LikeKey;
import com.startup.entity.key.TagKey;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DBTest {

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;
    @Autowired
    private UserLikeService userLikeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private MessageInfoService messageInfoService;

    @Autowired
    private MessageService messageService;

    @Test
    // db 통합 테스트
    public void dbTest() {

        // user insert
        User user = User.builder()
                .userId("test")
                .name("test")
                .registerNumber("test")
                .build();
        User user2 = User.builder()
                .userId("test2")
                .name("test2")
                .registerNumber("test2")
                .build();
        userService.add(user);
        userService.add(user2);

        User findUser = userService.find("test");
        Assertions.assertThat(findUser.getName()).isEqualTo("test");

        // board insert
        Board board = Board.builder()
                .contents("하하하")
                .userId(user.getUserId())
                .build();

        boardService.add(board);

        Board findBoard = boardService.find(1);
        Assertions.assertThat(findBoard.getContents()).isEqualTo("하하하");

        // insert user like into board
        UserLike userLike = UserLike.builder()
                .userId(user.getUserId())
                .boardId(findBoard.getId())
                .build();

        userLikeService.addUserLikeToBoard(userLike);

        LikeKey likeKey = LikeKey.builder()
                .boardId(findBoard.getId())
                .userId(user.getUserId())
                .build();

        UserLike findLike = userLikeService.findUserLike(likeKey);

        Assertions.assertThat(findLike).isNotNull();

        // insert tag into board

        Tag tag = Tag.builder().boardId(board.getId()).contents("하위").build();
        tagService.add(tag);

        List<Tag> tagList = tagService.find(board.getId());

        Assertions.assertThat(tagList.size()).isNotEqualTo(0);

        // create message info

        MessageInfo messageInfo = MessageInfo.builder()
                .fromUserId(user.getUserId())
                .toUserId(user2.getUserId())
                .build();

        messageInfoService.add(messageInfo);

        List<MessageInfo> messageInfos = messageInfoService.findSendMessageInfo(user);

        Assertions.assertThat(messageInfos.size()).isNotEqualTo(0);

        // sendMessage

        Message message = Message.builder()
                .messageInfoId(messageInfos.get(0).getId())
                .contents("안녕 메세지를 보냈어")
                .build();

        messageService.send(message);

        List<Message> messages = messageService.findMessages(messageInfo);

        Assertions.assertThat(messages.size()).isNotEqualTo(0);
    }

}