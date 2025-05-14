package com.yb.cloudPictrueBackend.exception;

import com.yb.cloudPictrueBackend.common.BaseResponse;
import com.yb.cloudPictrueBackend.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */


@RestControllerAdvice   //全局异常处理类，用于拦截带有 @RestController 注解的类中抛出的异常。
@Slf4j
public class GlobalExceptionHandler {

    //捕获自定义的 BusinessException 异常。
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException", e);
        return Result.error(e.getCode(), e.getMessage());
    }

    //捕获所有运行时异常。
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return Result.error(ErrorCode.SYSTEM_ERROR, "系统错误");
    }

    //全局兜底的异常处理方法，防止遗漏未捕获的异常：
    @ExceptionHandler(Throwable.class)
    public BaseResponse<?> throwableHandler(Throwable e) {
        log.error("Unknown Exception", e);
        return Result.error(ErrorCode.SYSTEM_ERROR, "服务器内部错误");
    }

}

