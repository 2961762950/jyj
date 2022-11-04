package com.bnzy.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常的统一处理
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})//带有此注解的异常全部由该类处理
@ResponseBody//发生异常由此类直接返回前端结果
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 异常处理
     */
    @ExceptionHandler(Exception.class)
    public R<String> exceptionHandler(Exception ex) {
        log.info("捕获{}异常", ex.getMessage());
        return R.error("未知错误");

    }
}
