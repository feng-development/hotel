package com.feng.hotel.manager.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feng.hotel.base.Pagination;
import com.feng.hotel.common.enums.RoomStatusEnum;
import com.feng.hotel.domain.Customer;
import com.feng.hotel.domain.Order;
import com.feng.hotel.domain.OrderRoom;
import com.feng.hotel.domain.OrderRoomCustomer;
import com.feng.hotel.domain.Room;
import com.feng.hotel.manager.IOrderManager;
import com.feng.hotel.request.CreateOrderRequest;
import com.feng.hotel.request.OrderQueryRequest;
import com.feng.hotel.request.RoomUserRequest;
import com.feng.hotel.response.CustomerResponse;
import com.feng.hotel.response.OrderResponse;
import com.feng.hotel.response.RoomCustomerResponse;
import com.feng.hotel.service.ICustomerService;
import com.feng.hotel.service.IIdCardService;
import com.feng.hotel.service.IOrderRoomCustomerService;
import com.feng.hotel.service.IOrderRoomService;
import com.feng.hotel.service.IOrderService;
import com.feng.hotel.service.IPayRecordService;
import com.feng.hotel.service.IRoomService;
import com.feng.hotel.utils.LambdaUtils;
import com.feng.hotel.utils.json.JsonUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @since 2021/8/6
 */
@Service
public class OrderManagerImpl implements IOrderManager {


    private final IOrderService orderService;

    private final IOrderRoomService orderRoomService;

    private final IIdCardService idCardService;

    private final IOrderRoomCustomerService orderRoomCustomerService;

    private final ICustomerService customerService;

    private final IRoomService roomService;

    private final IPayRecordService payRecordService;

    public OrderManagerImpl(IOrderService orderService, IOrderRoomService orderRoomService,
                            IIdCardService idCardService,
                            IOrderRoomCustomerService orderRoomCustomerService,
                            ICustomerService customerService,
                            IRoomService roomService,
                            IPayRecordService payRecordService) {
        this.orderService = orderService;
        this.orderRoomService = orderRoomService;
        this.idCardService = idCardService;
        this.orderRoomCustomerService = orderRoomCustomerService;
        this.customerService = customerService;
        this.roomService = roomService;
        this.payRecordService = payRecordService;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void save(CreateOrderRequest request, Long userNo) {
        // 验证房间是否可以入住
        List<Room> rooms = roomService.queryByIds(request.getRoomUsers().stream().map(RoomUserRequest::getRoomId).collect(Collectors.toSet()));
        rooms.forEach(e -> RoomStatusEnum.valueOf(e.getStatus()).validateNextStatus(RoomStatusEnum.USING));

        // 添加订单
        Order order = this.orderService.save(request, userNo);

        // 添加首款记录
        payRecordService.save(order.getId(), request.getTotalPrice() - request.getMortgage(), userNo);

        for (RoomUserRequest roomUser : request.getRoomUsers()) {

            //添加订单房间管理
            OrderRoom save = orderRoomService.save(order.getId(), roomUser, userNo);

            //添加房间订单用户关联
            orderRoomCustomerService.saveBatch(save.getId(), roomUser.getCustomerId(), userNo);

            //修改此房间的状态
            roomService.using(request.getRoomUsers().stream().map(RoomUserRequest::getRoomId).collect(Collectors.toSet()), order.getId(), userNo);
        }
    }


    @Override
    public Pagination<OrderResponse> page(OrderQueryRequest orderQueryRequest) {
        //分页获取订单
        IPage<Order> page = this.orderService.page(new Page<>(orderQueryRequest.getPage(), orderQueryRequest.getOffset()));

        return LambdaUtils.page(page, list -> {

            //获取房间信息
            Set<Long> orderIds = list.stream().map(Order::getId).collect(Collectors.toSet());
            List<OrderRoom> orderRooms = orderRoomService.queryByOrderIds(orderIds);
            Map<Long, OrderRoom> orderRoomMap = LambdaUtils.map(orderRooms, OrderRoom::getId);
            Map<Long, Room> roomMap = LambdaUtils.map(roomService.queryByIds(orderRoomMap.keySet()), Room::getId);

            List<OrderRoomCustomer> orderRoomCustomers = orderRoomCustomerService.queryByOrderRoomId(orderRooms.stream().map(OrderRoom::getId).collect(Collectors.toSet()));

            //获取客户信息
            List<Customer> customers = customerService.queryByIds(orderRoomCustomers.stream().map(OrderRoomCustomer::getCustomerId).collect(Collectors.toSet()));
            Map<Long, Customer> customerMap = LambdaUtils.map(customers, Customer::getId);

            Map<Long, List<Customer>> longListMap = LambdaUtils.mapList(orderRoomCustomers, e -> orderRoomMap.get(e.getOrderRoomId()).getRoomId(), e -> customerMap.get(e.getCustomerId()));

            //组装返回值
            Map<Long, List<RoomCustomerResponse>> roomCustomerMap = LambdaUtils.mapList(orderRooms, OrderRoom::getOrderId, e -> {
                Room room = roomMap.get(e.getRoomId());
                RoomCustomerResponse convert = JsonUtils.convert(room, RoomCustomerResponse.class);
                convert.setCustomerResponses(JsonUtils.convertList(longListMap.get(e.getRoomId()), CustomerResponse.class));
                return convert;
            });

            return list.stream().map(e -> {
                OrderResponse convert = JsonUtils.convert(e, OrderResponse.class);
                convert.setRoomCustomerResponses(roomCustomerMap.get(e.getId()));
                return convert;
            }).collect(Collectors.toList());
        });
    }
}
