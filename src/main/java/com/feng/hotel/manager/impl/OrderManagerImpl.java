package com.feng.hotel.manager.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feng.hotel.base.Pagination;
import com.feng.hotel.common.enums.RoomStatusEnum;
import com.feng.hotel.domain.Customer;
import com.feng.hotel.domain.Order;
import com.feng.hotel.domain.OrderCustomer;
import com.feng.hotel.domain.OrderRoom;
import com.feng.hotel.domain.Room;
import com.feng.hotel.manager.IOrderManager;
import com.feng.hotel.request.CreateOrderRequest;
import com.feng.hotel.request.OrderQueryRequest;
import com.feng.hotel.request.RoomUserRequest;
import com.feng.hotel.response.CustomerResponse;
import com.feng.hotel.response.IdCardResult;
import com.feng.hotel.response.OrderResponse;
import com.feng.hotel.response.RoomCustomerResponse;
import com.feng.hotel.service.ICustomerService;
import com.feng.hotel.service.IIdCardService;
import com.feng.hotel.service.IOrderCustomerService;
import com.feng.hotel.service.IOrderRoomService;
import com.feng.hotel.service.IOrderService;
import com.feng.hotel.service.IRoomService;
import com.feng.hotel.utils.LambdaUtils;
import com.feng.hotel.utils.json.JsonUtils;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Administrator
 * @since 2021/8/6
 */
@Service
public class OrderManagerImpl implements IOrderManager {


  private final IOrderService orderService;

  private final IOrderRoomService orderRoomService;

  private final IIdCardService idCardService;

  private final IOrderCustomerService orderCustomerService;

  private final ICustomerService customerService;

  private final IRoomService roomService;
  ;

  public OrderManagerImpl(IOrderService orderService, IOrderRoomService orderRoomService,
      IIdCardService idCardService,
      IOrderCustomerService orderCustomerService,
      ICustomerService customerService, IRoomService roomService) {
    this.orderService = orderService;
    this.orderRoomService = orderRoomService;
    this.idCardService = idCardService;
    this.orderCustomerService = orderCustomerService;
    this.customerService = customerService;
    this.roomService = roomService;
  }

  @Override
  @Transactional
  public void save(CreateOrderRequest request, Long userNo) {
//    验证房间是否可以入住
    List<Room> rooms = roomService.queryByIds(request.getRoomUsers().stream().map(RoomUserRequest::getRoomId).collect(Collectors.toSet()));
    rooms.forEach(e -> RoomStatusEnum.valueOf(e.getStatus()).validateNextStatus(RoomStatusEnum.USING));

    //添加订单
    Order order = this.orderService.save(request, userNo);

    for (RoomUserRequest roomUser : request.getRoomUsers()) {

      for (String path : roomUser.getPath()) {
        //获取用户身份信息
        IdCardResult idCardResult = idCardService.idCardRecognition(path);
        //尝试添加用户
        Customer customer = customerService.trySave(idCardResult, path);
        //添加订单房间客户关联
        orderCustomerService.save(roomUser.getRoomId(), order.getId(), customer.getId(), userNo);
      }

      //添加订单房间管理
      orderRoomService.save(order.getId(), roomUser, userNo);

      //修改此房间的状态
      roomService.updateStatus(request.getRoomUsers().stream().map(RoomUserRequest::getRoomId).collect(
          Collectors.toSet()), RoomStatusEnum.USING, userNo);

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
      Map<Long, Room> roomMap = LambdaUtils.map(roomService.queryByIds(orderRooms.stream().map(OrderRoom::getRoomId).collect(Collectors.toSet())), Room::getId);

      //获取客户信息
      List<OrderCustomer> orderCustomers = orderCustomerService.queryByOrderId(orderIds);
      List<Customer> customers = customerService
          .queryByIds(orderCustomers.stream().map(OrderCustomer::getCustomerId).collect(
              Collectors.toSet()));
      Map<Long, Customer> customerMap = LambdaUtils.map(customers, Customer::getId);
      Map<Long, List<CustomerResponse>> orderCustomerId = LambdaUtils
          .mapList(orderCustomers, OrderCustomer::getOrderId, e -> JsonUtils.convert(customerMap.get(e.getOrderId()), CustomerResponse.class));

      //组装返回值
      Map<Long, List<RoomCustomerResponse>> roomCustomerMap = LambdaUtils.mapList(orderRooms, OrderRoom::getOrderId, e -> {
        Room room = roomMap.get(e.getRoomId());
        RoomCustomerResponse convert = JsonUtils.convert(room, RoomCustomerResponse.class);
        convert.setCustomerResponses(orderCustomerId.get(e.getOrderId()));
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
