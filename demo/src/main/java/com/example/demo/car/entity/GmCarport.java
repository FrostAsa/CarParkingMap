package com.example.demo.car.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@TableName("gm_carport")
public class GmCarport  {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    @NotNull( message =  "name")
    private String name;

    @NotNull( message =  "phone")
    private String phone;

    @NotNull( message =  "address")
    private String address;

    private String lng;

    private String lan;
    @NotNull( message =  "startTime")
    private String startTime;

    @NotNull( message =  "startTime")
    private String endTime;

    private Integer status;

    @NotNull( message =  "price")
    private BigDecimal price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }



    @Override
    public String toString() {
        return "GmCarport{" +
        "id=" + id +
        ", userId=" + userId +
        ", name=" + name +
        ", phone=" + phone +
        ", address=" + address +
        ", lng=" + lng +
        ", lan=" + lan +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", status=" + status +
        ", price=" + price +
        "}";
    }
}
