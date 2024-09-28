package com.github.gluhov.accountmanagementservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class AccountManagementServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
