package com.fly.excel;

import com.fly.excel.aspect.MyExcelResponseAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Map;

@SpringBootApplication
public class MyExcelHelperBootApplication implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(MyExcelHelperBootApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // 查看切面类是否注入到容器中了
        Map<String, MyExcelResponseAspect> beansOfType = applicationContext.getBeansOfType(MyExcelResponseAspect.class);
        System.out.println(beansOfType);
    }
}
