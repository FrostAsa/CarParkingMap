package com.example.demo.controller;


import com.example.demo.config.UserUtils;
import com.example.demo.entity.MyUser;
import com.example.demo.service.UserService;
import com.example.demo.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class BaseController {

    @Autowired
    private UserService userDAO;

    public MyUser getUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        String username="";
        try {
            UserDetails userDetails = (UserDetails) context.getAuthentication().getPrincipal();
            username = userDetails.getUsername();

        }catch (Exception e){
            username= (String) context.getAuthentication().getPrincipal();
        }


        MyUser user = userDAO.getByUsername(username);
        return user;
    }

    public ResultUtil success() {
        return ResultUtil.success();
    }

    public ResultUtil success(int code, String msg, Object date) {
        return ResultUtil.success(code, msg, date);
    }

    public ResultUtil success(Object date) {
        return ResultUtil.success(date);
    }

    public ResultUtil success(int code, String msg) {
        return ResultUtil.success(code, msg);
    }
}
