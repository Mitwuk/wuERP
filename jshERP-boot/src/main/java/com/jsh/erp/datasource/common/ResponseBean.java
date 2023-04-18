package com.jsh.erp.datasource.common;

import com.alibaba.fastjson.annotation.JSONField;
import com.jsh.erp.constants.ExceptionConstants;

import java.io.Serializable;

/**
 * Controller 统一返回对象
 */
public class ResponseBean<T> implements Serializable {

    private static final long serialVersionUID = 7048691672612601L;


    /**
     * 状态码
     */
    @JSONField(ordinal = 0)
    private int code;

    /**
     * 信息对象
     */
    @JSONField(ordinal = 1)
    private String msg;

    /**
     * 结果对象
     */
    @JSONField(ordinal = 2)
    private T data;

    public ResponseBean() {
        this(ExceptionConstants.HTTP_OK_CODE, ExceptionConstants.HTTP_OK_MESSAGE, null);
    }


    public ResponseBean(int code, String msg) {
        this(code, msg, null);
    }

    public ResponseBean(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public ResponseBean<T> data(T data) {
        this.data = data;
        return this;
    }


    public static <T> ResponseBean<T> ok() {
        return new ResponseBean<>(ExceptionConstants.HTTP_OK_CODE, ExceptionConstants.HTTP_OK_MESSAGE);
    }

    public static <T> ResponseBean<T> ok(T data) {
        return new ResponseBean<>(ExceptionConstants.HTTP_OK_CODE, ExceptionConstants.HTTP_OK_MESSAGE, data);
    }


}