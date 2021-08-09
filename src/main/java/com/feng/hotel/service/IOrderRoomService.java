package com.feng.hotel.service;

import com.feng.hotel.domain.OrderRoom;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.hotel.request.RoomUserRequest;

/**
 * <p>
 * 订单房间  服务类
 * </p>
 *
 * @author feng
 * @since 2021-08-07
 */
public interface IOrderRoomService extends IService<OrderRoom> {

  /**
   * 添加订单与房间的管理
   *
   * @param orderId 订单id
   * @param userNo  用户id
   * @param request 房间信息
   */
  void save(Long orderId, RoomUserRequest request, Long userNo);
}
