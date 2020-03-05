package com.example.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
        "com.example.demo.login",
        "com.example.demo.admin",
        "com.example.demo.main",
        "com.example.demo.board",
})
public class AppConfig {
}
