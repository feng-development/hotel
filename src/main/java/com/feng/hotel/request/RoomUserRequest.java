package com.feng.hotel.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Administrator
 * @since 2021/8/5
 */

@Data
@ApiModel(value = "房间人请求对象")
public class RoomUserRequest {

  @ApiModelProperty(value = "余额")
  private BigDecimal balance;


  @ApiModelProperty(value = "身份证图片url")
  private String idImage;



}
