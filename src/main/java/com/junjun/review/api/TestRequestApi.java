package com.junjun.review.api;

import org.springframework.web.bind.annotation.*;

@RestController
public class TestRequestApi {

    //Request Parameter 방식
    @GetMapping("/test/param")
    public String requestParam(
            @RequestParam("name") String name,
            @RequestParam("age") Integer age
    ){
        return "Hello, Request Param, I am " + name + ", " + age;
    }

    //Path Variable 방식
    @GetMapping("/test/path/{name}/{age}") //여기에 쓰는 방식을 Path 라고 하는데 즉 이 Path 를 변수처럼 사용한다는 의미이다.
    public String requestPathVariable(
            @PathVariable("name") String name,
            @PathVariable("age") Integer age
    ){
        return "Hello, Path Variable I am " + name + ", " + age;
    }

    //Request Body 방식  보통  Request Body 는 POST 나 PUT 맵핑으로 많이 사용한다
    @PostMapping("/test/body")
    public String requestBody(
            @RequestBody TestRequestBody request
    ){
        return "Hello,  , I am  " + request.name + ", " + request.age;
    }
    //Request Body 방식을 이용하기 위해서는 클래스를 하나 추가해 주어야 한다
    public static class TestRequestBody{
        String name;
        Integer age;

        public TestRequestBody(String name, Integer age) {
            this.name = name;
            this.age = age;
        }
    }



}
