package com.feng.hotel.controller;


import com.feng.hotel.base.Pagination;
import com.feng.hotel.base.entity.response.Result;
import com.feng.hotel.common.BaseController;
import com.feng.hotel.manager.IOrderManager;
import com.feng.hotel.request.CreateOrderRequest;
import com.feng.hotel.request.OrderQueryRequest;
import com.feng.hotel.response.OrderResponse;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 订单  前端控制器
 * </p>
 *
 * @author feng
 * @since 2021-08-07
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    private final IOrderManager orderManager;

    public OrderController(IOrderManager orderManager) {
        this.orderManager = orderManager;
    }


    @PostMapping(value = "save")
    @ApiOperation(value = "保存订单")
    public Result<?> save(@RequestBody CreateOrderRequest request) {
        orderManager.save(request, this.getUserNo());
        return Result.success();
    }

    @PostMapping(value = "page")
    @ApiOperation("查询历史订单")
    public Result<Pagination<OrderResponse>> page(@RequestAttribute @Valid OrderQueryRequest orderQueryRequest) {
        return Result.success(orderManager.page(orderQueryRequest));
    }

}
