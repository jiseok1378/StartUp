package com.startup.controller;

import com.startup.security.config.SecurityConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class) // Spring boot와 Junit 사이의 연결자. Junit 실행시 SpringRunner라는 스프링 실행자 사용
@WebMvcTest(controllers = TestController.class) // Web (Spring MVC)에만 집중할 수 있는 어노테이션, Controller, ControllerAdvice 사용 가능.
@AutoConfigureDataJpa
@ComponentScan(basePackages =
        {
                "com.startup.security.config",
                "com.startup.security.jwt"
        })
public class TestControllerTest {
    @Autowired
    private MockMvc mvc; // 웹 API 테스트시 사용, HTTP, GET, POST 등에 대해 테스트 가능

    @Test
    public void test1() throws Exception {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("value", "+");
        MvcResult result =  mvc.perform(get("/").params(map)) // get 요청을 / 에 보내라
                .andExpect(status().isOk()) // response status를 검증 ok 가 아닐시 테스트 실패
                .andExpect(content().string("Hello world")) // response body 검증, return 되는 값이 Hello world 이면 테스트 성공
                .andReturn();

        System.out.println(result.getResponse().getContentAsString()); // response body를 꺼내와 string으로 보여줌
        System.out.println(result.getRequest().getRequestURL().toString()); // response body를 꺼내와 string으로 보여줌
    }
}