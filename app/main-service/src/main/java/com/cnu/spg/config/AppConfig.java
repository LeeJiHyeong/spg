package com.cnu.spg.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
        "com.cnu.spg.user",
        "com.cnu.spg.admin",
        "com.cnu.spg.main",
        "com.cnu.spg.board",
        "com.cnu.spg"
})
public class AppConfig {
}
