package com.feng.hotel.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 * @author Administrator
 * @since 2021/8/12
 */
@Data
@ApiModel(value = "订单简略信息")
public class OrderResponse {

  @ApiModelProperty(value = "id")
  private Long id;


  @ApiModelProperty(value = "订单号")
  private String orderNo;


  @ApiModelProperty(value = "余额")
  private BigDecimal balance;


  @ApiModelProperty(value = "订单状态 lodging：入住中，out离开，closure:关闭")
  private String status;


  @ApiModelProperty(value = "总价(已交的钱)")
  private BigDecimal totalPrice;


  @ApiModelProperty(value = "押金")
  private BigDecimal mortgage;


  @ApiModelProperty(value = "创建时间")
  private Date createTime;

  @ApiModelProperty(value = "房间客户列表")
  private List<RoomCustomerResponse> roomCustomerResponses;
}
