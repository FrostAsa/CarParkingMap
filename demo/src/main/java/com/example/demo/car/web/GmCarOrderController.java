package com.example.demo.car.web;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.car.entity.GmCarOrder;
import com.example.demo.car.service.IGmCarOrderService;
import com.example.demo.controller.BaseController;
import com.example.demo.entity.MyUser;
import com.example.demo.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/GmCarOrderOrder")
public class GmCarOrderController extends BaseController {

	@Autowired
	IGmCarOrderService service;



	@GetMapping("getList")
	@ResponseBody
	public ResultUtil getLIst(Integer type){
		MyUser user = getUser();
		QueryWrapper<GmCarOrder> wrapper = new QueryWrapper<>();
		if (type==0){
			wrapper.lambda().eq(GmCarOrder::getCarUserId,user.getId());
		}else {
			wrapper.lambda().eq(GmCarOrder::getCarPortUserId,user.getId());
		}
		List<GmCarOrder> list = service.list(wrapper);
		return ResultUtil.success(list);
	}

	@PostMapping("add")
	@ResponseBody
	public ResultUtil getLIst(@RequestBody  @Valid GmCarOrder info){
		MyUser user = getUser();
		service.add(info,user);
		return ResultUtil.success();
	}
	@GetMapping("cancel/{id}")
	@ResponseBody
	public ResultUtil cancel(@PathVariable("id") Integer id){
		service.cancel(id);
		return ResultUtil.success();
	}

}
