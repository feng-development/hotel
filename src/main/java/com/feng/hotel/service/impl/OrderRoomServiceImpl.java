package com.feng.hotel.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.hotel.base.Constants.Valid;
import com.feng.hotel.common.HotelConstants;
import com.feng.hotel.domain.OrderRoom;
import com.feng.hotel.mapper.OrderRoomMapper;
import com.feng.hotel.request.RoomUserRequest;
import com.feng.hotel.service.IOrderRoomService;
import com.feng.hotel.utils.IdWorkerUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 订单房间  服务实现类
 * </p>
 *
 * @author feng
 * @since 2021-08-07
 */
@Service
public class OrderRoomServiceImpl extends ServiceImpl<OrderRoomMapper, OrderRoom> implements
    IOrderRoomService {

    @Override
    public OrderRoom save(Long orderId, RoomUserRequest request, Long userNo) {
        return this.save(orderId, 0L, request.getType(), request.getBalance(), request.getRoomId(), userNo);
    }

    @Override
    public List<OrderRoom> query(Set<Long> roomIds, String status) {
        return this.list(Wrappers.<OrderRoom>lambdaQuery()
            .in(OrderRoom::getRoomId, roomIds)
            .eq(OrderRoom::getStatus, status)
        );
    }

    @Override
    public List<OrderRoom> queryByOrderIds(Set<Long> orderIds) {
        return this.list(Wrappers.<OrderRoom>lambdaQuery()
            .in(OrderRoom::getOrderId, orderIds)
        );
    }

    @Override
    public void updateStatus(Long orderId, Long roomId, String orderRoomStatus, Long userNo) {
        this.update(
            Wrappers.<OrderRoom>lambdaUpdate()
                .set(OrderRoom::getStatus, orderRoomStatus)
                .set(OrderRoom::getModifier, userNo)
                .set(OrderRoom::getModifyTime, new Date())
                .eq(OrderRoom::getOrderId, orderId)
                .eq(OrderRoom::getRoomId, roomId)
        );
    }

    @Override
    public OrderRoom save(Long orderId, Long pid, String type, Integer price, Long roomId, Long userNo) {
        Date date = new Date();
        OrderRoom orderRoom = new OrderRoom()
            .setId(IdWorkerUtils.generateLongId())
            .setOrderId(orderId)
            .setRoomId(roomId)
            .setBeginTime(date)
            .setType(type)
            .setStatus(HotelConstants.OrderStatus.LODGING)
            .setPrice(price)
            .setCreateTime(date)
            .setModifyTime(date)
            .setCreator(userNo)
            .setModifier(userNo)
            .setValid(Valid.NORMAL);
        this.save(orderRoom);
        return orderRoom;
    }
}
