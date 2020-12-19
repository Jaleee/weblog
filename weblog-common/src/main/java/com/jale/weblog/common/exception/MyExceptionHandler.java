package com.jale.weblog.common.exception;

import com.jale.weblog.common.commonobjects.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = WeblogException.class)
    public R customException(WeblogException e) {
        return R.error(e.getMsg());
    }

}
