package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.MyUser;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService  extends IService<MyUser>, UserDetailsService {
   MyUser getByUsername(String username);

   Page page(MyUser param);

   void add(MyUser param);
}
