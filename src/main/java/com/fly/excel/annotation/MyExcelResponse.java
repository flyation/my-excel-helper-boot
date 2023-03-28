package com.fly.excel.annotation;

import com.alibaba.excel.support.ExcelTypeEnum;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcelResponse {

    String name() default "";

    ExcelTypeEnum suffix() default ExcelTypeEnum.XLSX;
}
