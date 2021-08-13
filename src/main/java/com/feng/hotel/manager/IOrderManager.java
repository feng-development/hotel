package com.feng.hotel.manager;

import com.feng.hotel.base.Pagination;
import com.feng.hotel.request.CreateOrderRequest;
import com.feng.hotel.request.OrderQueryRequest;
import com.feng.hotel.response.OrderResponse;

/**
 * @author Administrator
 * @since 2021/8/6
 */
public interface IOrderManager {

  /**
   * 创建订单
   *
   * @param request 订单参数
   * @param userNo  当前操作人
   */
  void save(CreateOrderRequest request, Long userNo);



  /**
   * 查询订单查询
   *
   * @param orderQueryRequest 订单查询参数
   * @return 分页订单
   */
  Pagination<OrderResponse> page(OrderQueryRequest orderQueryRequest);
}
