package com.example.demo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.demo.config.UserVo;
import com.example.demo.entity.MyUser;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, MyUser> implements UserService, UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Override
    public MyUser getByUsername(String username) {
        LambdaQueryWrapper<MyUser> eq = Wrappers.<MyUser>lambdaQuery().eq(MyUser::getUsername, username);
        MyUser one = super.getOne(eq);
        return one;
    }

    @Override
    public Page page(MyUser param) {
        return null;
    }

    @Override
    public void add(MyUser param) {
            super.save(param);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = getByUsername(username);
        if(myUser ==null) {
            throw new UsernameNotFoundException(username);
        }
        List<GrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_MEDIUM"));
        authorities.add(new SimpleGrantedAuthority("meter_add"));

        return new UserVo(myUser.getUsername(), myUser.getPassword(), authorities);
    }
}
