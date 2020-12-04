package com.jale.weblog.common.exception;

import com.jale.weblog.common.systemcomponents.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public R customException(Exception e) {
        return R.error(e.getMessage());
    }

}
