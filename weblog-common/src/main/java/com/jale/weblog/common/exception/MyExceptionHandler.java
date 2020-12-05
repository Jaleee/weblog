package com.jale.weblog.common.exception;

import com.jale.weblog.common.commonobjects.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public R customException(Exception e) {
        e.printStackTrace();
        return R.error(e.getMessage());
    }

}
