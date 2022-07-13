package com.example.demo.car.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;


import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;


@TableName("gm_car_order")
public class GmCarOrder {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    private Integer orderNumber;

    private   Integer  carUserId;

    private Integer carId;

    @NotNull( message =  "carPortId")
    private Integer carPortId;

    private Integer carPortUserId;


    @NotNull( message =  "startTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @NotNull( message =  "endTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    private BigDecimal price;

    private String address;
    private String carNumber;

    private Integer status;

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }
    public Integer getCarPortId() {
        return carPortId;
    }

    public void setCarPortId(Integer carPortId) {
        this.carPortId = carPortId;
    }
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCarUserId() {
        return carUserId;
    }

    public void setCarUserId(Integer carUserId) {
        this.carUserId = carUserId;
    }

    public Integer getCarPortUserId() {
        return carPortUserId;
    }

    public void setCarPortUserId(Integer carPortUserId) {
        this.carPortUserId = carPortUserId;
    }

    @Override
    public String toString() {
        return "GmCarOrder{" +
        "id=" + id +
        ", orderNumber=" + orderNumber +
        ", carId=" + carId +
        ", carPortId=" + carPortId +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", price=" + price +
        ", address=" + address +
        ", status=" + status +
        "}";
    }
}
