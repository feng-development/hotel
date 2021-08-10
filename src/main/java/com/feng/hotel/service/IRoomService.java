package com.feng.hotel.service;

import com.feng.hotel.common.enums.RoomStatusEnum;
import com.feng.hotel.domain.Room;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 房间  服务类
 * </p>
 *
 * @author evision
 * @since 2021-08-02
 */
public interface IRoomService extends IService<Room> {

  /**
   * 获取房间列表
   *
   * @param status 房间状态
   * @return 房间列表
   */
  List<Room> list(String status);

  /**
   * 修改房间状态
   *
   * @param roomIds 房间id
   * @param status  状态
   * @param userNo  用户id
   */
  void updateStatus(List<Long> roomIds, RoomStatusEnum status, Long userNo);
}
