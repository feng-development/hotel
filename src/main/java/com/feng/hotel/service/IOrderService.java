package com.feng.hotel.service;

import com.feng.hotel.domain.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.hotel.request.CreateOrderRequest;

/**
 * <p>
 * 订单  服务类
 * </p>
 *
 * @author feng
 * @since 2021-08-07
 */
public interface IOrderService extends IService<Order> {

    /** 创建订单
     * @param request 订单参数
     * @param userNo 操作人
     */
    Order save(CreateOrderRequest request, Long userNo);

}
