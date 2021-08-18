package com.feng.hotel.request;

import com.feng.hotel.base.entity.request.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Administrator
 * @since 2021/8/11
 */
@ApiModel(value = "订单查询对象")
public class OrderQueryRequest extends PageRequest {

    @ApiModelProperty(value = "订单类型")
    private String type;


}
