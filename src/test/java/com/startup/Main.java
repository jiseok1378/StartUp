package com.startup;

import com.startup.controller.TestControllerTest;
import com.startup.service.DBTest;
import com.startup.service.LoginServiceImplTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestControllerTest.class,
        LoginServiceImplTest.class,
        DBTest.class
})

public class Main {
}
