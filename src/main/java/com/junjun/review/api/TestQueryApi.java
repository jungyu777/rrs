package com.junjun.review.api;

import com.junjun.review.model.TestEntity;
import com.junjun.review.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequiredArgsConstructor
@RestController
public class TestQueryApi {
    private final TestService testService;
    @GetMapping("/test/query/jpa")
    public List<TestEntity> queryJpa(){
        return testService.findAllByNameByJPA("junjun");
    }

    @GetMapping("/test/query/querydsl")
    public List<TestEntity>  queryQuerydsl(){
        return testService.findAllByNameByQuerydsl("junjun");
    }
}
