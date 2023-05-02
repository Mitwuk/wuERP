package com.jsh.erp.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class BusinessCommonException extends RuntimeException {

    private int code;
    private String msg;

    public BusinessCommonException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
