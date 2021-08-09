package com.feng.hotel.manager;

import com.feng.hotel.request.CreateOrderRequest;

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
   * 获取详情
   *
   * @param id 订单id
   */
  void getDetail(Long id);
}
