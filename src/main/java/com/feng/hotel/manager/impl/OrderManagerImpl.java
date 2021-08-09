package com.feng.hotel.manager.impl;

import com.feng.hotel.domain.Customer;
import com.feng.hotel.domain.Order;
import com.feng.hotel.manager.IOrderManager;
import com.feng.hotel.request.CreateOrderRequest;
import com.feng.hotel.request.RoomUserRequest;
import com.feng.hotel.response.IdCardResult;
import com.feng.hotel.service.ICustomerService;
import com.feng.hotel.service.IIdCardService;
import com.feng.hotel.service.IOrderCustomerService;
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

  private final IOrderRoomService orderRoomService;

  private final IIdCardService idCardService;

  private final IOrderCustomerService orderCustomerService;

  private final ICustomerService customerService;
  ;

  public OrderManagerImpl(IOrderService orderService, IOrderRoomService orderRoomService,
      IIdCardService idCardService,
      IOrderCustomerService orderCustomerService,
      ICustomerService customerService) {
    this.orderService = orderService;
    this.orderRoomService = orderRoomService;
    this.idCardService = idCardService;
    this.orderCustomerService = orderCustomerService;
    this.customerService = customerService;
  }

  @Override
  public void save(CreateOrderRequest request, Long userNo) {
    //添加订单

    Order order = this.orderService.save(request, userNo);

    for (RoomUserRequest roomUser : request.getRoomUsers()) {

      for (String path : roomUser.getPath()) {
        //获取用户身份信息
        IdCardResult idCardResult = idCardService.idCardRecognition(path);
        //尝试添加用户
        Customer customer = customerService.trySave(idCardResult, path);

        //添加订单房间客户关联
        orderCustomerService.save(roomUser.getRoomId(),order.getId(),customer.getId());
      }


      //添加订单房间管理
      orderRoomService.save(order.getId(), roomUser, userNo);

    }


  }

  @Override
  public void getDetail(Long id) {

  }
}
