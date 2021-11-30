package com.feng.hotel.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 * @since 2021/8/9
 */
@ApiModel(value = "百度云识别结果")
@Data
public class IdCardResult {

    @ApiModelProperty(value = "住址")
    private String address;

    @ApiModelProperty(value = "身份证号码")
    private String idNum;

    @ApiModelProperty(value = "用户名称")
    private String name;

    @ApiModelProperty(value = "性别")
    private String cex;

}
