package com.jale.weblog.shopping.app;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.jale.weblog")
@MapperScan("com.jale.weblog.shopping.service.mapper")
@DubboComponentScan(basePackages = {"com.jale.weblog"})
@EnableDubbo
@EnableAsync // 开启异步任务
@EnableScheduling //开启定时任务
public class WeblogShoppingApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeblogShoppingApplication.class, args);
    }

}
