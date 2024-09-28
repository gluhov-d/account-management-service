package com.github.gluhov.accountmanagementservice;

import org.springframework.boot.SpringApplication;

public class TestAccountManagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(AccountManagementServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
