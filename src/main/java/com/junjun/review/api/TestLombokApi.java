package com.junjun.review.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestLombokApi {
    @GetMapping("/test/lombok")
    public TestLombokResponseBody testLombok(){
        return new TestLombokResponseBody("jungyu",30);
    }
    @Getter
    @AllArgsConstructor
    public static class TestLombokResponseBody{
        String name;
        Integer age;
    }
}
