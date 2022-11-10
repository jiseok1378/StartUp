package com.startup;

import com.startup.controller.TestControllerTest;
import com.startup.service.DBTest;
import com.startup.service.UserControllerTest;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.test.context.transaction.TestTransaction;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestControllerTest.class,
        UserControllerTest.class,
        DBTest.class
})

public class Main {
    @Before
    public void init(){
        TestTransaction.flagForCommit();
        TestTransaction.flagForRollback();
    }
}
