package com.feng.hotel.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

/**
 * @author Administrator
 * @since 2021/8/5
 */

@Data
@ApiModel(value = "房间人请求对象")
public class RoomUserRequest {

    @ApiModelProperty(value = "房间价格")
    private Integer balance;

    @ApiModelProperty(value = "房间id")
    private Long roomId;

    @ApiModelProperty(value = "客户id")
    private List<Long> customerId;

    @ApiModelProperty(value = "房间状态 DAILY:日租 MONTHLY：月组 HOURLY：钟点")
    private String type;
}
