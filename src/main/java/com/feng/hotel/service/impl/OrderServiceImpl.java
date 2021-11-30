package com.feng.hotel.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.feng.hotel.base.Constants;
import com.feng.hotel.common.HotelConstants;
import com.feng.hotel.domain.Order;
import com.feng.hotel.mapper.OrderMapper;
import com.feng.hotel.request.CreateOrderRequest;
import com.feng.hotel.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.hotel.utils.IdWorkerUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

/**
 * <p>
 * 订单  服务实现类
 * </p>
 *
 * @author feng
 * @since 2021-08-07
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Override
    public Order save(CreateOrderRequest request, Long userNo) {
        Date date = new Date();
        Order order = new Order()
            .setId(IdWorkerUtils.generateLongId())
            .setOrderNo(IdWorkerUtils.generateStringId())
            .setCreator(userNo)
            .setModifier(userNo)
            .setCreateTime(date)
            .setModifyTime(date)
            .setValid(Constants.Valid.NORMAL)
            .setMortgage(request.getMortgage())
            .setStatus(HotelConstants.OrderStatus.LODGING)
            .setTotalPrice(request.getTotalPrice())
            .setBalance(request.getTotalPrice()-request.getMortgage());

        this.save(order);

        return order;
    }

    @Override
    public List<Order> queryByIds(Set<Long> orderIds) {
        if (CollectionUtils.isEmpty(orderIds)) {
            return Collections.emptyList();
        }
        return this.list(Wrappers.<Order>lambdaQuery()
            .eq(Order::getOrderNo, orderIds)
        );
    }

    @Override
    public void updateStatus(Long orderId, String status, Long userNo) {
        this.update(
            Wrappers.<Order>lambdaUpdate()
                .set(Order::getStatus, status)
                .set(Order::getModifier, userNo)
                .set(Order::getModifyTime, new Date())
                .eq(Order::getId, orderId)
        );
    }
}
