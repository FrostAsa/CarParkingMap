package com.example.demo.car.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.car.entity.GmCar;


public interface IGmCarService extends IService<GmCar> {

	GmCar getByUser(Integer id);
}
