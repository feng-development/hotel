package com.feng.hotel.manager.impl;

import com.feng.hotel.domain.Room;
import com.feng.hotel.manager.IRoomManager;
import com.feng.hotel.request.RoomRequest;
import com.feng.hotel.response.RoomResponse;
import com.feng.hotel.service.IRoomService;
import com.feng.hotel.utils.json.JsonUtils;
import java.util.Collections;
import java.util.List;
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

  public RoomManagerImpl(IRoomService roomService) {
    this.roomService = roomService;
  }

  @Override
  public void save(RoomRequest roomRequest1) {
    roomService.save(JsonUtils.convert(roomRequest1, Room.class));
  }


  public List<RoomResponse> list(String status) {
    List<Room> list = roomService.list(status);
    if (CollectionUtils.isEmpty(list)) {
      return Collections.emptyList();
    }

    Set<Long> roomIds = list.stream().map(Room::getId).collect(Collectors.toSet());

    return JsonUtils.convertList(list, RoomResponse.class);
  }
}
