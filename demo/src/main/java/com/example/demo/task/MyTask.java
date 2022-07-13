package com.example.demo.task;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.car.entity.GmCarOrder;
import com.example.demo.car.service.IGmCarOrderService;


public class MyTask implements Runnable{



	private IGmCarOrderService iGmCarOrderService;

	private GmCarOrder data;

	public MyTask(IGmCarOrderService iGmCarOrderService, GmCarOrder data) {
		this.iGmCarOrderService=iGmCarOrderService;
		this.data=data;
	}

	@Override
	public void run() {
		UpdateWrapper<GmCarOrder> wrapper = new UpdateWrapper<>();
		wrapper.lambda().set(GmCarOrder::getStatus,1).eq(GmCarOrder::getId,data.getId());
		iGmCarOrderService.update(wrapper);
	}
}
