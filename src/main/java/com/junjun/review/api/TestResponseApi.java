package com.junjun.review.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestResponseApi {
    @GetMapping("/test/response/string")
    public String stringResponse(){
        return "This is String";
    }

    @GetMapping("/test/response/json")
    public TestResponseBody jsonResponse(){
        return new  TestResponseBody("jungyu",20);
    }

    /**
     *  TestResponseBody 에는 보통 생성자와 갯터 셋터가 같이 있다
     */

    public static class TestResponseBody{
        String name;
        Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public TestResponseBody(String name, Integer age) {
            this.name = name;
            this.age = age;
        }
    }

}
