package com.example.demo.car.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.car.entity.GmCar;
import com.example.demo.car.mapper.GmCarMapper;
import com.example.demo.car.service.IGmCarService;
import org.springframework.stereotype.Service;


@Service
public class GmCarServiceImpl extends ServiceImpl<GmCarMapper, GmCar> implements IGmCarService {

	@Override
	public GmCar getByUser(Integer userId) {
		return 	super.getOne(Wrappers.lambdaQuery(GmCar.class).eq(GmCar::getUserId,userId));
	}
}
