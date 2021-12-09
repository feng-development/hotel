package com.feng.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.hotel.domain.OrderRoomCustomer;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 订单客户  服务类
 * </p>
 *
 * @author feng
 * @since 2021-11-30
 */
public interface IOrderRoomCustomerService extends IService<OrderRoomCustomer> {

    /**
     * 获取订单客户
     *
     * @param collect 订单房间id
     * @return 订单房间下的客户关联
     */
    List<OrderRoomCustomer> queryByOrderRoomId(Set<Long> collect);

    /**
     * 添加
     *
     * @param orderRoomId 订单房间关联id
     * @param customerIds 客户id
     * @param userNo      当前用户id
     */
    void saveBatch(Long orderRoomId, List<Long> customerIds, Long userNo);
}
