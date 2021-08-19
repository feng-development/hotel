package com.feng.hotel.manager.impl;

import com.feng.hotel.base.Constants;
import com.feng.hotel.common.HotelConstants;
import com.feng.hotel.common.enums.HotelEnum;
import com.feng.hotel.common.enums.RoomStatusEnum;
import com.feng.hotel.domain.Customer;
import com.feng.hotel.domain.Order;
import com.feng.hotel.domain.OrderCustomer;
import com.feng.hotel.domain.OrderRoom;
import com.feng.hotel.domain.Room;
import com.feng.hotel.manager.IRoomManager;
import com.feng.hotel.request.RoomRequest;
import com.feng.hotel.response.CustomerResponse;
import com.feng.hotel.response.RoomResponse;
import com.feng.hotel.service.ICustomerService;
import com.feng.hotel.service.IOrderCustomerService;
import com.feng.hotel.service.IOrderRoomService;
import com.feng.hotel.service.IOrderService;
import com.feng.hotel.service.IRoomService;
import com.feng.hotel.utils.Assert;
import com.feng.hotel.utils.LambdaUtils;
import com.feng.hotel.utils.json.JsonUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author Administrator
 * @since 2021/8/3
 */
@Service
public class RoomManagerImpl implements IRoomManager {

    private final IRoomService roomService;

    private final IOrderService orderService;

    private final IOrderCustomerService orderCustomerService;

    private final ICustomerService customerService;

    private final IOrderRoomService orderRoomService;

    public RoomManagerImpl(IRoomService roomService,
                           IOrderService orderService, IOrderCustomerService orderCustomerService,
                           ICustomerService customerService, IOrderRoomService orderRoomService) {
        this.roomService = roomService;
        this.orderService = orderService;
        this.orderCustomerService = orderCustomerService;
        this.customerService = customerService;
        this.orderRoomService = orderRoomService;
    }

    @Override
    public void save(RoomRequest roomRequest, Long userNo) {
        Date date = new Date();
        Room room = JsonUtils.convert(roomRequest, Room.class);
        room.setStatus(RoomStatusEnum.NORMAL.name());
        room.setCreator(userNo);
        room.setModifier(userNo);
        room.setCreateTime(date);
        room.setModifyTime(date);
        room.setValid(Constants.Valid.NORMAL);
        roomService.save(room);
    }


    @Override
    public List<RoomResponse> list(String status) {
        //查询房间
        List<Room> list = roomService.list(status);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        //查询再住订单
        Set<Long> orderIds = list.stream().map(Room::getOrderId).collect(Collectors.toSet());

        //查询在住人
        List<OrderCustomer> orderCustomers = orderCustomerService.queryByOrderId(orderIds);
        Map<Long, List<Long>> orderCustomerMaplist = LambdaUtils
            .mapList(orderCustomers, OrderCustomer::getOrderId, OrderCustomer::getCustomerId);

        List<Customer> customers = customerService.queryByIds(
            orderCustomers.stream().map(OrderCustomer::getCustomerId).collect(Collectors.toSet()));
        Map<Long, Customer> customerMap = LambdaUtils.map(customers, Customer::getId);

        return list.stream().map(e -> {
            RoomResponse convert = JsonUtils.convert(e, RoomResponse.class);
            //再住客户
            List<Long> customerIds = orderCustomerMaplist.get(e.getOrderId());
            if (!CollectionUtils.isEmpty(customerIds)) {
                convert.setCustomers(JsonUtils.convertList(customerIds.stream().map(customerMap::get)
                    .collect(Collectors.toList()), CustomerResponse.class));
            }

            return convert;
        }).collect(Collectors.toList());


    }

    @Override
    public void updateStatus(Long id, String status, Long userId) {
        this.roomService.updateStatus(Collections.singleton(id), RoomStatusEnum.valueOf(status), userId);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void quit(Long roomId, Long userNo) {
        Room room = this.roomService.getById(roomId);
        Assert.assertNotNull(room, HotelEnum.ROOM_NOT_EXIST_ERROR);
        Assert.assertNotNull(room.getOrderId(), HotelEnum.ROOM_STATUS_ERROR);


        //查看订单下是否只有一个在住房间 只有一个 整个订单退出
        List<OrderRoom> orderRooms = orderRoomService.queryByOrderIds(Collections.singleton(room.getOrderId()));
        List<OrderRoom> orderRoomList = orderRooms.stream().filter(e -> Objects.equals(e.getStatus(), RoomStatusEnum.USING.name())).collect(Collectors.toList());
        if (orderRoomList.size() == 1) {
            orderService.updateStatus(room.getOrderId(), HotelConstants.OrderStatus.OUT, userNo);
        }

        //修改房间状态
        roomService.updateStatus(Collections.singleton(roomId), RoomStatusEnum.READY_CLEAN, userNo);
        //修改房间订单关联状态
        orderRoomService.updateStatus(room.getOrderId(), roomId, HotelConstants.OrderStatus.OUT, userNo);

    }
}
