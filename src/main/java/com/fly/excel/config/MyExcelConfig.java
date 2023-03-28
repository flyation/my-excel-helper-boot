package com.fly.excel.config;

import com.fly.excel.aspect.MyExcelResponseAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyExcelConfig {

    @Bean
    public MyExcelResponseAspect myExcelResponseAspect() {
        System.out.println("加载MyExcelResponseAspect...");
        return new MyExcelResponseAspect();
    }
}
