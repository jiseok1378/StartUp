package com.startup.service;

import com.startup.entity.Tag;
import com.startup.entity.key.TagKey;
import com.startup.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public Tag add(Tag tag){
        return tagRepository.save(tag);
    }
    public List<Tag> find(int boardId){
        return tagRepository.findByBoardId(boardId);
    }
}
