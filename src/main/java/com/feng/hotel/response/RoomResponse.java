package com.feng.hotel.response;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * @author Administrator
 * @since 2021/8/3
 */
@Data
public class RoomResponse {

  @ApiModelProperty(value = "id")
  private Long id;

  @ApiModelProperty(value = "房间号")
  private String roomNo;


  @ApiModelProperty(value = "价格")
  private BigDecimal price;

  @ApiModelProperty(value = "状态 normal:正常，ready_clean：打扫中，repair：维修中，using：使用中")
  private String status;

  @ApiModelProperty(value = "当前入住订单id 退房时清除")
  private Long orderId;

  @ApiModelProperty(value = "余额， 一个订单多个房间显示总余额")
  private BigDecimal balance;

  @ApiModelProperty(value = "在住人")
  private List<CustomerResponse> customers;

}
