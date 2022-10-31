package com.startup.service;


import com.startup.entity.TestEntity;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestServiceTest {
    @Autowired
    private TestService service;
    @Test
    public void test() {
        final String test = "test";
        TestEntity testVal =
                TestEntity.builder()
                        .id(test)
                        .name(test)
                        .password(test)
                        .build();

        service.add(testVal);
        TestEntity findVal = service.find(test);
        Assertions.assertThat(findVal.getName()).isEqualTo(test);
    }

}