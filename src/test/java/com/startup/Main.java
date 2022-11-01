package com.startup;

import com.startup.controller.TestControllerTest;
import com.startup.service.DBTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestControllerTest.class,
        DBTest.class
})

public class Main {
}
