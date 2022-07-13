package com.example.demo.car.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.car.entity.GmCar;
import com.example.demo.car.entity.GmCarOrder;
import com.example.demo.car.entity.GmCarport;
import com.example.demo.car.mapper.GmCarOrderMapper;
import com.example.demo.car.service.IGmCarOrderService;
import com.example.demo.car.service.IGmCarService;
import com.example.demo.car.service.IGmCarportService;
import com.example.demo.entity.MyUser;
import com.example.demo.task.TaskUtil;
import com.example.demo.util.AppUtil;
import com.example.demo.util.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;

import java.util.Date;

@Service
public class GmCarOrderServiceImpl extends ServiceImpl<GmCarOrderMapper, GmCarOrder> implements IGmCarOrderService {

	@Autowired
	IGmCarportService carportService;
	@Autowired
	IGmCarService carService;
	@Autowired
	TaskUtil taskUtil;

	@Override
	public void add(GmCarOrder info, MyUser user) {
		GmCarport carport = carportService.getById(info.getCarPortId());
		Date startTime = info.getStartTime();
		Date endTime = info.getEndTime();
		if (carport.getStatus()==1){
			throw  new BizException("Parking Space is in use!");
		}
		GmCar byUser = carService.getByUser(user.getId());
		if (byUser==null){
			throw  new BizException("Please upload vehicle information");
		}
		info.setCarId(byUser.getId());
		info.setAddress(carport.getAddress());
		boolean effectiveDate = AppUtil.isEffectiveDate(startTime, carport.getStartTime(), carport.getEndTime());
		boolean effectiveDate1 = AppUtil.isEffectiveDate(endTime, carport.getStartTime(), carport.getEndTime());
		if (effectiveDate&&effectiveDate1){
			info.setCarUserId(user.getId());
			info.setCarPortUserId(carport.getUserId());
			info.setStatus(0);
			super.save(info);
			UpdateWrapper<GmCarport> wrapper = new UpdateWrapper<>();
			wrapper.lambda().eq(GmCarport::getId,carport.getId()).set(GmCarport::getStatus,1);
			carportService.update(wrapper);
			taskUtil.addTask(this,info,info.getEndTime());

		}else {
			throw  new BizException("Time Not Available!");
		}
	}

	@Override
	public void cancel(Integer id) {
		UpdateWrapper<GmCarport> wrapper = new UpdateWrapper<>();
		wrapper.lambda().eq(GmCarport::getId,id).set(GmCarport::getStatus,0);
		carportService.update(wrapper);
		super.update(Wrappers.lambdaUpdate(GmCarOrder.class).eq(GmCarOrder::getId,id).set(GmCarOrder::getStatus,2));
		taskUtil.stopTask(id);

	}




}
