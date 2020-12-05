package com.jale.weblog.shopping.app;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.jale.weblog")
@MapperScan("com.jale.weblog.shopping.service.mapper")
@DubboComponentScan(basePackages = {"com.jale.weblog"})
@EnableDubbo
public class WeblogShoppingApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeblogShoppingApplication.class, args);
    }

}
