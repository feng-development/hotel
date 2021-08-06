package com.feng.hotel.controller;

import com.feng.hotel.base.entity.response.Result;
import com.feng.hotel.common.BaseController;
import com.feng.hotel.manager.IOrderManager;
import com.feng.hotel.request.CreateOrderRequest;
import javax.websocket.server.PathParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @since 2021/8/5
 */
@RestController
@RequestMapping(value = "order")
public class OrderController extends BaseController {

  private final IOrderManager orderManager;

  public OrderController(IOrderManager orderManager) {
    this.orderManager = orderManager;
  }

  @GetMapping(value = "/{id}")
  public Result<?> get(@PathParam("id") Long id) {
    return Result.success();
  }

  @PostMapping(value = "save")
  public Result<?> save(@RequestBody CreateOrderRequest request) {
    orderManager.save(request,this.getUserNo());
    return Result.success()
  }
}
