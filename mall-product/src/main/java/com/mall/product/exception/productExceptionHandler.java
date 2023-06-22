package com.mall.product.exception;

import com.mall.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static com.mall.common.exception.ExceptionCodeEnum.UNKNOWN_EXCEPTION;
import static com.mall.common.exception.ExceptionCodeEnum.VALID_EXCEPTION;


@Slf4j
@RestControllerAdvice
public class productExceptionHandler {


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handlerValidException(MethodArgumentNotValidException methodArgumentNotValidException, BindingResult bindingResult){

        log.error("数据校验异常",methodArgumentNotValidException);
        Map<String,String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach((fieldError)->{
            errorMap.put(fieldError.getField(),fieldError.getDefaultMessage());
        });
        return R.error(VALID_EXCEPTION.getCode(),VALID_EXCEPTION.getMsg()).put("data",errorMap);
    }



    @ExceptionHandler(value = Throwable.class)
    public R handlerException(Throwable throwable){


        log.error("未知错误",throwable);
        return R.error(UNKNOWN_EXCEPTION.getCode(), UNKNOWN_EXCEPTION.getMsg());
    }


}
