package com.feng.hotel.service;

import com.feng.hotel.domain.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.hotel.request.CreateOrderRequest;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 订单  服务类
 * </p>
 *
 * @author feng
 * @since 2021-08-07
 */
public interface IOrderService extends IService<Order> {

    /**
     * 创建订单
     *
     * @param request 订单参数
     * @param userNo  操作人
     */
    Order save(CreateOrderRequest request, Long userNo);

    /**
     * 根据id获取订单
     *
     * @param orderIds 订单id
     * @return 订单
     */
    List<Order> queryByIds(Set<Long> orderIds);

    /**
     * 修改订单状态
     *
     * @param orderId 订单id
     * @param status  状态
     * @param userNo  修改人
     */
    void updateStatus(Long orderId, String status, Long userNo);
}
