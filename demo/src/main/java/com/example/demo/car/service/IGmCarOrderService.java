package com.example.demo.car.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.car.entity.GmCarOrder;
import com.example.demo.entity.MyUser;


public interface IGmCarOrderService extends IService<GmCarOrder> {

	void add(GmCarOrder info, MyUser user);

	void cancel(Integer id);
}
