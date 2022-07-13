package com.example.demo.handler;


import com.example.demo.util.ResultUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/*
* exception handle */

@ControllerAdvice
public class CloudExceptionHandler {
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public ResultUtil handleOtherExceptions(final Exception ex) {

        return  ResultUtil.error(1,ex.getMessage());
    }
}
