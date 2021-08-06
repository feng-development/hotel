package com.feng.hotel.controller;

import com.feng.hotel.base.entity.response.Pagination;
import com.feng.hotel.base.entity.response.Result;
import com.feng.hotel.manager.IRoomManager;
import com.feng.hotel.request.RoomRequest;
import com.feng.hotel.response.RoomResponse;
import io.swagger.annotations.ApiOperation;
import javax.websocket.server.PathParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @since 2021/8/3
 */
@RestController
@RequestMapping(value = "room")
public class RoomController {

  private final IRoomManager roomManager;

  public RoomController(IRoomManager roomManager) {
    this.roomManager = roomManager;
  }

  @PostMapping(value = "save")
  @ApiOperation(value = "添加房间")
  public Result<?> save(@RequestBody RoomRequest roomRequest) {
    roomManager.save(roomRequest);
    return Result.success();
  }

  @GetMapping(value = "list/{status}")
  @ApiOperation(value = "房间列表")
  public Result<Pagination<RoomResponse>> list(@PathParam(value = "status") String status) {
    return Result.success(roomManager.list(status));
  }

}
