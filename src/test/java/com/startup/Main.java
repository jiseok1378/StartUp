package com.startup;

import com.startup.controller.TestControllerTest;
import com.startup.service.TestServiceTest;
import com.startup.service.UserServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestControllerTest.class,
        TestServiceTest.class,
        UserServiceTest.class
})
public class Main {
}
