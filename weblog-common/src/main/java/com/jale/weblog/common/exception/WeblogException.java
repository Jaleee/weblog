package com.jale.weblog.common.exception;

import lombok.Data;

@Data
public class WeblogException extends RuntimeException {

    private String msg;

    public WeblogException(String msg) {
        super(msg);
        this.msg = msg;
    }

}
