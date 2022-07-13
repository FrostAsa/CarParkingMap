package com.example.demo.car.web;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.car.entity.GmCar;
import com.example.demo.car.service.IGmCarService;
import com.example.demo.controller.BaseController;
import com.example.demo.entity.MyUser;
import com.example.demo.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/gmCar")
public class GmCarController  extends BaseController {

	@Autowired
	IGmCarService service;



	@GetMapping("getList")
	@ResponseBody
	public ResultUtil getLIst(){
		QueryWrapper<GmCar> wrapper = new QueryWrapper<>();
		List<GmCar> list = service.list(wrapper);
		return ResultUtil.success(list);
	}
	@GetMapping("getByUser")
	@ResponseBody
	public ResultUtil getByUser(){
		MyUser user = getUser();
		GmCar one = service.getByUser(user.getId());
		return ResultUtil.success(one);
	}

	@PostMapping("add")
	@ResponseBody
	public ResultUtil getLIst(@RequestBody @Valid GmCar info){
		MyUser user = getUser();
		info.setUserId(user.getId());
		GmCar one = service.getByUser(user.getId());
		if (one!=null){
			info.setId(one.getId());
		}
		service.saveOrUpdate(info);
		return ResultUtil.success();
	}

}
