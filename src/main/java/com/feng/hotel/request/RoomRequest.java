package com.feng.hotel.request;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

import lombok.Data;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @since 2021/8/3
 */
@Data
public class RoomRequest {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "房间号")
    private String roomNo;


    @ApiModelProperty(value = "价格")
    private BigDecimal price;


    @ApiModelProperty(value = "状态 normal:正常，ready_clean：打扫中，repair：维修中，quit：退房")
    private String status;

    @ApiModelProperty(value = "当前入住订单id 退房时清除")
    private String orderId;

}
