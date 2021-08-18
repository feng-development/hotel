package com.feng.hotel.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * @author Administrator
 * @since 2021/8/12
 */
@ApiModel(value = "房间与客户管理信息")
@Data
public class RoomCustomerResponse {

    @ApiModelProperty(value = "房间id")
    private Long id;

    @ApiModelProperty(value = "房间号")
    private String roomNo;

    @ApiModelProperty(value = "房间类型")
    private String type;

    @ApiModelProperty(value = "开始入住时间")
    private Date beginTime;

    @ApiModelProperty(value = "离开时间")
    private Date engTime;

    @ApiModelProperty(value = "房间状态")
    private String status;

    @ApiModelProperty(value = "上个房子的id")
    private Long pid;

    @ApiModelProperty(value = "客户信息")
    private List<CustomerResponse> customerResponses;
}
