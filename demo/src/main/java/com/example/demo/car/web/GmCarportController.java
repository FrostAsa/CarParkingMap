package com.example.demo.car.web;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.car.entity.GmCar;
import com.example.demo.car.entity.GmCarport;
import com.example.demo.car.service.IGmCarService;
import com.example.demo.car.service.IGmCarportService;
import com.example.demo.controller.BaseController;
import com.example.demo.entity.MyUser;
import com.example.demo.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/gmCarport")
public class GmCarportController  extends BaseController {
	@Autowired
	IGmCarportService service;



	@GetMapping("getList")
	@ResponseBody
	public ResultUtil getLIst(){
		QueryWrapper<GmCarport> wrapper = new QueryWrapper<>();
		List<GmCarport> list = service.list(wrapper);
		return ResultUtil.success(list);
	}

	@PostMapping("add")
	@ResponseBody
	public ResultUtil getLIst(@RequestBody   @Valid  GmCarport info){
		MyUser user = getUser();
		info.setUserId(user.getId());
		info.setStatus(0);
		service.saveOrUpdate(info);
		return ResultUtil.success();
	}
}
