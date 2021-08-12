package com.feng.hotel.service;

import com.feng.hotel.domain.OrderRoom;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.hotel.request.RoomUserRequest;
import java.util.List;
import java.util.Set;

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

  /**
   * 查询订单房间
   *
   * @param roomIds 房间id
   * @param status 房间状态
   */
  List<OrderRoom> query(Set<Long> roomIds, String status);

  /**根据订单查询
   * @param orderIds  订单id
   * @return 订单房间管理表
   */
  List<OrderRoom> queryByOrderIds(Set<Long> orderIds);
}
