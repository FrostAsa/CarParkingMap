package com.example.demo.car.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.hibernate.validator.constraints.NotBlank;


import java.io.Serializable;

//This is the Car driver entity.

@TableName("gm_car")
public class GmCar  {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * User id
     */
    private Integer userId;

    /**
     * Driver name
     */
    @NotBlank(message = "Driver Name")
    private String carName;

    /**
     * Driver name
     */
    @NotBlank(message = "Driver phone number")
    private String phone;

    /**
     * license plate
     */
    @NotBlank(message = "license plate")
    private String carNumber;

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
    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }


    @Override
    public String toString() {
        return "GmCar{" +
        "id=" + id +
        ", userId=" + userId +
        ", carName=" + carName +
        ", phone=" + phone +
        ", carNumber=" + carNumber +
        "}";
    }
}
