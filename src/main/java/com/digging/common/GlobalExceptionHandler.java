package com.digging.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;


// 全局异常处理
// 如果类上加有 @RestController、@Controller注解(annotations的属性值)的类中有方法抛出异常，由GlobalExceptionHander来处理异常
@ControllerAdvice
@ResponseBody  // 将结果封装成JSON数据并返回
@Slf4j
public class GlobalExceptionHandler {

    // 解决 字段username被唯一索引约束的情况下，添加相同的username，抛出SQLIntegrityConstraintViolationException 的全局异常
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<String> exceptionHandler(SQLIntegrityConstraintViolationException e) {
        log.info(e.getMessage());

        if (e.getMessage().contains("Duplicate entry")) {
            String[] split = e.getMessage().split(" ");
            String msg = "用户名" + split[2] + "已存在";
            return Result.error(msg);
        }
        return Result.error("操作失败！");
    }

    //解决上传文件大小报错
    @ExceptionHandler(FileSizeLimitExceededException.class)
    public Result<String> exceptionHandler(FileSizeLimitExceededException e)
    {
        log.info(e.getMessage());

        return Result.error("图片大小超过1M,请重新选择！");
    }

    //解决空指针异常报错
    @ExceptionHandler(NullPointerException.class)
    public Result<String> exceptionHandler(NullPointerException e)
    {
        log.info(e.getMessage());
        return Result.error("字段不能为空！");
    }

    @ExceptionHandler(MyCustomException.class)
    public Result<String> exceptionHandler(MyCustomException e) {
        log.info(e.getMessage());

        return Result.error(e.getMessage());

    }
}

