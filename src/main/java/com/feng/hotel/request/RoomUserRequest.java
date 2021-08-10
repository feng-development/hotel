package com.feng.hotel.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Administrator
 * @since 2021/8/5
 */

@Data
@ApiModel(value = "房间人请求对象")
public class RoomUserRequest {

  @ApiModelProperty(value = "房间价格")
  private BigDecimal balance;

  @ApiModelProperty(value = "房间id")
  private Long roomId;

  @ApiModelProperty(value = "身份证图片url")
  private List<String> path;


}