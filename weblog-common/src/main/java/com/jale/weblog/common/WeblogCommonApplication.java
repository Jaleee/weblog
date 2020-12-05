package com.jale.weblog.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class WeblogCommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeblogCommonApplication.class, args);
    }

}
