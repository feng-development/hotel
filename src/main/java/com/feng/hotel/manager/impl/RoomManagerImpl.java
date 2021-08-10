package com.feng.hotel.manager.impl;

import com.feng.hotel.domain.Order;
import com.feng.hotel.domain.OrderCustomer;
import com.feng.hotel.domain.OrderRoom;
import com.feng.hotel.domain.Room;
import com.feng.hotel.manager.IRoomManager;
import com.feng.hotel.request.RoomRequest;
import com.feng.hotel.response.RoomResponse;
import com.feng.hotel.service.IOrderCustomerService;
import com.feng.hotel.service.IOrderService;
import com.feng.hotel.service.IRoomService;
import com.feng.hotel.utils.json.JsonUtils;
import java.util.Collections;
import java.util.List;
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

  public RoomManagerImpl(IRoomService roomService,
      IOrderService orderService, IOrderCustomerService orderCustomerService) {
    this.roomService = roomService;
    this.orderService = orderService;
    this.orderCustomerService = orderCustomerService;
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
    Set<String> orderIds = list.stream().map(Room::getOrderId).collect(Collectors.toSet());
    List<Order> orders = orderService.queryByIds(orderIds);

    //查询在住人
    List<OrderCustomer> orderCustomers = orderCustomerService.queryByOrderId(orderIds);

    return list.stream().map(e -> {
      OrderRoom orderRoom = orderRoomMap.get(e.getId());
      RoomResponse convert = JsonUtils.convert(e, RoomResponse.class);
      if (Objects.nonNull(orderRoom)) {
        convert.setOrderId(orderRoom.getOrderId());
      }
      return convert;
    }).collect(Collectors.toList());


  }
}
