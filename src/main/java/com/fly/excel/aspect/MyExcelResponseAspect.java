package com.fly.excel.aspect;

import com.alibaba.excel.EasyExcel;
import com.fly.excel.annotation.MyExcelResponse;
import com.fly.excel.entity.DemoData;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.MediaTypeFactory;
import org.springframework.util.Assert;
import org.springframework.util.MimeType;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Aspect
public class MyExcelResponseAspect {

    @Pointcut("@annotation(com.fly.excel.annotation.MyExcelResponse)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 执行目标方法
        Object result = joinPoint.proceed();

        // 判断返回值是否为List
        if (!(result instanceof List)) {
            throw new RuntimeException("@MyResponseExcel返回值必须为List类型");
        }
        List<?> objList = (List<?>) result;
        Assert.notEmpty(objList, "列表不能为空！");

        // 计时
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        MyExcelResponse annotation = signature.getMethod().getAnnotation(MyExcelResponse.class);
        // 文件名
        String fileName = handleFilename(annotation);
        // contentType
        String contentType = MediaTypeFactory.getMediaType(fileName).map(MimeType::toString).orElse("application/vnd.ms-excel");

        // 生成excel，写出到输出流中
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setContentType(contentType);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        EasyExcel.write(response.getOutputStream(), DemoData.class)
                .sheet("模板")
                .doWrite(objList);

        stopWatch.stop();
        System.out.println("用时" + stopWatch.getTotalTimeMillis() + "ms");
//        return result;
    }

    /**
     * 处理文件名
     * @param annotation 当前注解
     */
    private String handleFilename(MyExcelResponse annotation) throws UnsupportedEncodingException {
        String name = annotation.name() + System.currentTimeMillis();
//        String fileName = String.format("%s%s", URLEncoder.encode(name, StandardCharsets.UTF_8.name()), annotation.suffix().getValue());
        String fileName = String.format("%s%s", URLEncoder.encode(name, "UTF-8"), ".xlsx");
        return fileName;
    }
}
