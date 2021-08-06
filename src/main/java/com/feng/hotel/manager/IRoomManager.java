package com.feng.hotel.manager;

import com.feng.hotel.request.RoomRequest;
import com.feng.hotel.response.RoomResponse;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @since 2021/8/3
 */

public interface IRoomManager {

  /**
   * 添加房间
   *
   * @param roomRequest 房间参数
   */
  void save(RoomRequest roomRequest);

  /**
   * 获取房间列表
   *
   * @param status 房间状态
   * @return 房间列表
   */
  List<RoomResponse> list(String status);
}
