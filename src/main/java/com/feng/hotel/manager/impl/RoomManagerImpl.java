package com.feng.hotel.manager.impl;

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
import com.feng.hotel.service.IOrderService;
import com.feng.hotel.service.IRoomService;
import com.feng.hotel.utils.LambdaUtils;
import com.feng.hotel.utils.json.JsonUtils;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
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

  public RoomManagerImpl(IRoomService roomService,
      IOrderService orderService, IOrderCustomerService orderCustomerService,
      ICustomerService customerService) {
    this.roomService = roomService;
    this.orderService = orderService;
    this.orderCustomerService = orderCustomerService;
    this.customerService = customerService;
  }

  @Override
  public void save(RoomRequest roomRequest) {
    roomService.save(JsonUtils.convert(roomRequest, Room.class));
  }


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
}
