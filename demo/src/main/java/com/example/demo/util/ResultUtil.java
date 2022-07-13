package com.example.demo.util;

import java.io.Serializable;

public class ResultUtil implements Serializable {
    public static final int SUCCESSCODE = 200;
    public static final String SUCCESSMSG = "Successful operation";

    private int code;
    private String msg;
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResultUtil(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultUtil(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    public ResultUtil(Object data) {
        this.code = SUCCESSCODE;
        this.msg = SUCCESSMSG;
        this.data = data;
    }

    public ResultUtil() {
        this.code = SUCCESSCODE;
        this.msg = SUCCESSMSG;
        this.data = null;
    }

    public static ResultUtil success(int code, String msg, Object data) {
        return new ResultUtil(code, msg, data);
    }

    public static ResultUtil success(Object date) {
        return new ResultUtil(date);
    }

    public static ResultUtil success(int code, String msg) {
        return new ResultUtil(code, msg);
    }

    public static ResultUtil success() {
        return new ResultUtil();
    }

    public static ResultUtil error(int code, String msg) {
        return new ResultUtil(code, msg);
    }
}
