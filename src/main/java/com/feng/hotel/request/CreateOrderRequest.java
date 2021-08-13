package com.feng.hotel.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Administrator
 * @since 2021/8/5
 */
@Data
@ApiModel(value = "创建订单请求")
public class CreateOrderRequest {

  @ApiModelProperty(value = "房间住宿人")
  @NotEmpty(message = "房间不能为空")
  private List<RoomUserRequest> roomUsers;

  @ApiModelProperty(value = "押金")
  @NotNull(message = "押金不能为空")
  private BigDecimal mortgage;

  @ApiModelProperty(value = "总价(交的钱-押金)")
  @NotNull(message = "总价不能为空")
  private BigDecimal totalPrice;

  @ApiModelProperty(value = "订单号", hidden = true)
  private String orderNo;


}
