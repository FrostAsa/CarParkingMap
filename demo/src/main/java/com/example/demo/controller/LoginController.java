package com.example.demo.controller;

import com.example.demo.entity.MyUser;
import com.example.demo.service.UserService;
import com.example.demo.util.MD5Util;
import com.example.demo.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class LoginController extends BaseController {
    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }
    @RequestMapping("/driver")
    public String driver() {
        return "driver";
    }
    @RequestMapping("/owner")
    public String owner() {
        return "owner";
    }
    @PostMapping("/register")
    @ResponseBody
    public ResultUtil login(@RequestBody MyUser user){
        String md5 = MD5Util.getMD5(user.getPassword());
        user.setPassword(md5);
        userService.add(user);
       return ResultUtil.success();
    }

}
