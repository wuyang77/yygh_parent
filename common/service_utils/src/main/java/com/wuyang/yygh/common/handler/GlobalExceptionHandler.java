package com.wuyang.yygh.common.handler;

import com.wuyang.yygh.common.exception.YyghException;
import com.wuyang.yygh.common.result.R;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //全局统一异常处理
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public R error(Exception ex){
        ex.printStackTrace();
        log.error("全局异常"+ex.getLocalizedMessage());
        return R.error().message("出现了异常");
    }


    //特定异常处理
    @ExceptionHandler(value = ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException ex){
        ex.printStackTrace();
        return R.error().message("出现了除0异常");
    }
    //自定义异常
    @ExceptionHandler(value = YyghException.class)
    @ResponseBody
    public R error(YyghException ex){
        ex.printStackTrace();
        log.error("YyghException"+ex.getLocalizedMessage());
        return R.error().code(ex.getCode()).message(ex.getMessage());
    }
}













