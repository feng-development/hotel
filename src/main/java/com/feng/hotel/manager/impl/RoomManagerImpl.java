package com.feng.hotel.manager.impl;

import com.feng.hotel.domain.Room;
import com.feng.hotel.manager.IRoomManager;
import com.feng.hotel.request.RoomRequest;
import com.feng.hotel.response.RoomResponse;
import com.feng.hotel.service.IRoomService;
import com.feng.hotel.utils.json.JsonUtils;
import java.util.List;
import org.springframework.stereotype.Service;

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
  public void save(RoomRequest roomRequest) {
    roomService.save(JsonUtils.convert(roomRequest, Room.class));
  }


  public List<RoomResponse> list(String status) {
    return JsonUtils.convertList(roomService.list(status), RoomResponse.class);
  }
}
