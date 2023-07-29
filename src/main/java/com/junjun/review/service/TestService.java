package com.junjun.review.service;

import com.junjun.review.model.TestEntity;
import com.junjun.review.repository.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TestService {

    private final TestRepository testRepository;

    public void create(String name,Integer age){
        TestEntity testEntity = new TestEntity(name,age);
        testRepository.save(testEntity);
    }

    public void delete(Long id){
        TestEntity testEntity =testRepository.findById(id).get();
        testRepository.delete(testEntity);
    }
    public void update(Long id,String name, Integer age){
        TestEntity testEntity = testRepository.findById(id).orElseThrow();
        testEntity.changeNameAndAge(name,age);
        testRepository.save(testEntity);
    }
    public List<TestEntity> findAllByNameByJPA(String name){
         return testRepository.findAllByName(name);
    }
    public List<TestEntity> findAllByNameByQuerydsl(String name){
        return testRepository.findAllByNameQuerydsl(name);
    }
}
