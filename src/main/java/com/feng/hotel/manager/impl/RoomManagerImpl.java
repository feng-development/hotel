package com.feng.hotel.manager.impl;

import com.feng.hotel.base.Constants;
import com.feng.hotel.base.exception.BizException;
import com.feng.hotel.base.exception.EnumReturnStatus;
import com.feng.hotel.common.HotelConstants;
import com.feng.hotel.common.enums.HotelEnum;
import com.feng.hotel.common.enums.RoomStatusEnum;
import com.feng.hotel.domain.Customer;
import com.feng.hotel.domain.OrderRoom;
import com.feng.hotel.domain.OrderRoomCustomer;
import com.feng.hotel.domain.PayRecord;
import com.feng.hotel.domain.Room;
import com.feng.hotel.manager.IRoomManager;
import com.feng.hotel.request.RoomRequest;
import com.feng.hotel.request.RoomSwapRequest;
import com.feng.hotel.response.CustomerResponse;
import com.feng.hotel.response.RoomResponse;
import com.feng.hotel.service.ICustomerService;
import com.feng.hotel.service.IOrderRoomCustomerService;
import com.feng.hotel.service.IOrderRoomService;
import com.feng.hotel.service.IOrderService;
import com.feng.hotel.service.IPayRecordService;
import com.feng.hotel.service.IRoomService;
import com.feng.hotel.utils.Assert;
import com.feng.hotel.utils.LambdaUtils;
import com.feng.hotel.utils.json.JsonUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @since 2021/8/3
 */
@Service
public class RoomManagerImpl implements IRoomManager {

    private final IRoomService roomService;

    private final IOrderService orderService;

    private final IOrderRoomCustomerService orderRoomCustomerService;

    private final ICustomerService customerService;

    private final IOrderRoomService orderRoomService;

    private final IPayRecordService payRecordService;

    public RoomManagerImpl(IRoomService roomService,
                           IOrderService orderService,
                           IOrderRoomCustomerService orderRoomCustomerService,
                           ICustomerService customerService,
                           IOrderRoomService orderRoomService, IPayRecordService payRecordService) {
        this.roomService = roomService;
        this.orderService = orderService;
        this.orderRoomCustomerService = orderRoomCustomerService;
        this.customerService = customerService;
        this.orderRoomService = orderRoomService;
        this.payRecordService = payRecordService;
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


        List<RoomResponse> roomResponses = JsonUtils.convertList(list, RoomResponse.class);

        //查询再住订单
        Set<Long> orderIds = list.stream().map(Room::getOrderId).collect(Collectors.toSet());

        //查询订单关联的房间
        List<OrderRoom> orderRooms = this.orderRoomService.queryByOrderIds(orderIds).stream().filter(e -> !Objects.equals(e.getStatus(), HotelConstants.OrderStatus.CLOSE)).collect(Collectors.toList());
        Map<Long, OrderRoom> map = LambdaUtils.map(orderRooms, OrderRoom::getId);

        //总价
        int sumPrice = payRecordService.queryByOrderIds(orderIds).stream().mapToInt(PayRecord::getPrice).sum();


        //查询每个房间的在住人
        Collection<OrderRoomCustomer> orderRoomCustomers = this.orderRoomCustomerService.queryByOrderRoomId(map.keySet());

        //查询客户信息
        List<Customer> customers = customerService.queryByIds(
            orderRoomCustomers.stream().map(OrderRoomCustomer::getCustomerId).collect(Collectors.toSet()));
        Map<Long, Customer> customerMap = LambdaUtils.map(customers, Customer::getId);

        // key 订单房间关联id value 客户信息
        Map<Long, List<Customer>> longListMap = LambdaUtils.mapList(orderRoomCustomers, e -> {
            OrderRoom orderRoom = map.get(e.getOrderRoomId());
            return orderRoom.getRoomId();
        }, e -> customerMap.get(e.getCustomerId()));

        return roomResponses.stream().map(e -> {
            e.setCustomers(JsonUtils.convertList(longListMap.get(e.getId()), CustomerResponse.class));
            e.setBalance(1);
            return e;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void swap(RoomSwapRequest roomSwapRequest, Long userNo) {
        Date date = new Date();

        OrderRoom orderRoom = this.orderRoomService.getById(roomSwapRequest.getOrderRootId());
        if (Objects.isNull(orderRoom)) {
            throw new BizException(EnumReturnStatus.ROOM_NOT_EXIST);
        }

        Room room = this.roomService.getById(roomSwapRequest.getNewRootId());
        //判断房间是否处于正常状态
        if (!Objects.equals(roomSwapRequest.getNewRootId(), orderRoom.getRoomId()) && Objects.equals(room.getStatus(), RoomStatusEnum.NORMAL.name())) {
            throw new BizException(EnumReturnStatus.ROOM_STATUS_ERROR);
        }

        //修改原房间状态
        this.orderRoomService.updateById(
            orderRoom.setStatus(HotelConstants.OrderStatus.OUT)
                .setModifier(userNo)
                .setModifyTime(date)
                .setBeginTime(date)
        );

        //换到新房间
        this.orderRoomService.save(orderRoom.getOrderId(),orderRoom.getPid(),roomSwapRequest.getType(), roomSwapRequest.getPrice(),roomSwapRequest.getNewRootId(),userNo);

    }


    private void countPrice() {

    }
}
