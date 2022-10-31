package com.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

/**
 * create database startup
 * CREATE USER 'startup'@'localhost' IDENTIFIED BY '12345678'
 * GRANT ALL PRIVILEGES ON startup.* to startup@'localhost'
 */
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
