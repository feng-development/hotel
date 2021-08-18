package com.feng.hotel.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 * @since 2021/8/6
 */
@Data
@ApiModel(value = "登录对象")
public class AuthRequest {

    @ApiModelProperty(value = "登录名")
    private String loginName;

    @ApiModelProperty(value = "密码")
    private String pwd;

    @ApiModelProperty(value = "客户端")
    private String client;
}
