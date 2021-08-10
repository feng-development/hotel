package com.feng.hotel.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 * @since 2021/8/10
 */
@Data
public class CustomerResponse {

  @ApiModelProperty(value = "id")
  private Long id;

  @ApiModelProperty(value = "身份证")
  private String idNo;

  @ApiModelProperty(value = "姓名")
  private String name;

}
