package com.feng.hotel.manager.impl;

import com.feng.hotel.HotelApplicationTests;
import com.feng.hotel.manager.IOrderManager;
import com.feng.hotel.request.CreateOrderRequest;
import com.feng.hotel.request.RoomUserRequest;
import java.math.BigDecimal;
import java.util.Collections;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Administrator
 * @since 2021/8/10
 */
public class OrderManagerImplTest extends HotelApplicationTests {

  @Autowired
  private IOrderManager orderManager;

  @Test
  public void save() {
    CreateOrderRequest request = new CreateOrderRequest();
    request.setMortgage(new BigDecimal(60));
    request.setTotalPrice(new BigDecimal("60"));

    RoomUserRequest roomUserRequest = new RoomUserRequest();
    roomUserRequest.setRoomId(1L);
    roomUserRequest.setBalance(new BigDecimal(60));
    roomUserRequest.setPath(Collections.singletonList("E:/20210810085204.jpg"));
    request.setRoomUsers(Collections.singletonList(roomUserRequest));
    orderManager.save(request,123L);
  }

  @Test
  public void getDetail() {
  }
}