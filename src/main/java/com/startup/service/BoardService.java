package com.startup.service;

import com.startup.entity.Board;
import com.startup.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Board add(Board board){
        return boardRepository.save(board);
    }
    @Transactional
    public Board find(int boardId){

        return boardRepository.findById(boardId).orElseThrow(NoSuchElementException::new);
    }
    @Transactional
    public void remove(Board board){
        boardRepository.delete(board);
    }
}
