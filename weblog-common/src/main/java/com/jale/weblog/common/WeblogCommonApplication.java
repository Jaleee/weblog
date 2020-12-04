package com.jale.weblog.common;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@EnableDubbo
@ServletComponentScan
public class WeblogCommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeblogCommonApplication.class, args);
    }

}
