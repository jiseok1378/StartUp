package com.startup.repository;

import com.startup.entity.Tag;
import com.startup.entity.key.TagKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, TagKey> {

    List<Tag> findByBoardId(int boardId);
}
