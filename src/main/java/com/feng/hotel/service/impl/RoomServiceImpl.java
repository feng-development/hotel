package com.feng.hotel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.feng.hotel.common.enums.RoomStatusEnum;
import com.feng.hotel.domain.Room;
import com.feng.hotel.mapper.RoomMapper;
import com.feng.hotel.service.IRoomService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.sql.Wrapper;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 房间  服务实现类
 * </p>
 *
 * @author evision
 * @since 2021-08-02
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements IRoomService {

  @Override
  public List<Room> list(String status) {
    LambdaQueryWrapper<Room> wrapper = Wrappers.<Room>lambdaQuery();
    if (Objects.nonNull(status)) {
      wrapper.eq(Room::getStatus, status);
    }

    return this.list(wrapper);
  }

  @Override
  public void updateStatus(List<Long> roomIds, RoomStatusEnum status, Long userNo) {
    Wrappers.<Room>lambdaUpdate()
        .set(Room::getStatus, status)
        .set(Room::getModifyTime, new Date())
        .set(Room::getModifier, userNo)
        .in(Room::getId, roomIds);

  }
}
