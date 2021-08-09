package com.feng.hotel.manager.impl;

import com.feng.hotel.domain.Order;
import com.feng.hotel.manager.IOrderManager;
import com.feng.hotel.request.CreateOrderRequest;
import com.feng.hotel.request.RoomUserRequest;
import com.feng.hotel.service.IOrderRoomService;
import com.feng.hotel.service.IOrderService;
import com.feng.hotel.utils.IdWorkerUtils;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @since 2021/8/6
 */
@Service
public class OrderManagerImpl implements IOrderManager {


    private final IOrderService orderService;

    private final IOrderRoomService orderRoomService
        ;

    public OrderManagerImpl(IOrderService orderService, IOrderRoomService orderRoomService) {
        this.orderService = orderService;
        this.orderRoomService = orderRoomService;
    }

    @Override
    public void save(CreateOrderRequest request, Long userNo) {
        //添加订单
        String orderNo = IdWorkerUtils.generateStringId();
        request.setOrderNo(orderNo);
        Order save = this.orderService.save(request, userNo);

        for (RoomUserRequest roomUser : request.getRoomUsers()) {
            orderRoomService.save(orderNo,userNo)
        }


    }
}
