package com.junjun.review.api;

import com.junjun.review.service.TestService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class TestEntityApi {

    private final TestService testService;
    @PostMapping("/test/entity/create")
    public void createTestEntity(
            @RequestBody CreateTestEntityRequest request
    ){
        testService.create(request.getName(),request.getAge());
    }
    @DeleteMapping("/test/entity/{id}")
    public void deleteTestEntity(
        @PathVariable Long id
    ){
        testService.delete(id);
    }

    @Getter
    @AllArgsConstructor
    public static class CreateTestEntityRequest{
        private final String name;
        private final Integer age;
    }
    @PutMapping("/test/entity/{id}")
    public void putTestEntity(
            @PathVariable Long id,
            @RequestBody CreateTestEntityRequest request
    ){
        testService.update(id,request.getName(),request.getAge());
    }
}
