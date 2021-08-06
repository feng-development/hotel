package com.feng.hotel.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * @author Administrator
 * @since 2021/8/5
 */
@Data
@ApiModel(value = "创建订单请求")
public class CreateOrderRequest {

  @ApiModelProperty(value = "房间住宿人")
  private List<RoomUserRequest> RoomUsers;


  @ApiModelProperty(value = "押金")
  private BigDecimal margin;
}
