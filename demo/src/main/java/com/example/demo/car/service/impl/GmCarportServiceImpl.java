package com.example.demo.car.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.car.entity.GmCarport;
import com.example.demo.car.mapper.GmCarportMapper;
import com.example.demo.car.service.IGmCarportService;
import org.springframework.stereotype.Service;


@Service
public class GmCarportServiceImpl extends ServiceImpl<GmCarportMapper, GmCarport> implements IGmCarportService {

}
