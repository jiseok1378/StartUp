package com.startup.service;

import com.startup.entity.TestEntity;
import com.startup.repository.TestRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class TestService {
    private final TestRepository testRepository;

    public TestEntity add(TestEntity entity){
        return testRepository.save(entity);
    }
    public TestEntity find(String id){
        return testRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
}
