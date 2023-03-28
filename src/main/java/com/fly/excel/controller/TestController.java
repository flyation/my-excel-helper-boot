package com.fly.excel.controller;

import com.alibaba.excel.util.ListUtils;
import com.fly.excel.annotation.MyExcelResponse;
import com.fly.excel.entity.DemoData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class TestController {

    @MyExcelResponse(name = "测试文档")
    @GetMapping("/")
    public List<?> excel(String name) {
        System.out.println("excel...");
        return data();
    }

    private List<DemoData> data() {
        List<DemoData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.42);
            list.add(data);
        }
        return list;
    }
}
