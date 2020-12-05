package com.jale.weblog.common.commonobjects;

import lombok.Data;

@Data
public class R {

    private Integer code;
    private String msg;
    private Object content;

    public static R success() {
        R r = new R();
        r.code = 1;
        r.msg = "Request Successful";
        return r;
    }

    public static R success(Object content) {
        R r = new R();
        r.code = 1;
        r.msg = "Request Successful";
        r.content = content;
        return r;
    }

    public static R error() {
        R r = new R();
        r.code = 0;
        r.msg = "Request failure";
        return r;
    }

    public static R error(String msg) {
        R r = new R();
        r.code = 0;
        r.msg = msg;
        return r;
    }

}
