package com.feng.hotel.controller;

import com.feng.hotel.base.entity.response.Result;
import com.feng.hotel.common.BaseController;
import com.feng.hotel.manager.IRoomManager;
import com.feng.hotel.request.RoomRequest;
import com.feng.hotel.request.RoomSwapRequest;
import com.feng.hotel.response.RoomResponse;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Administrator
 * @since 2021/8/3
 */
@RestController
@RequestMapping(value = "room")
public class RoomController extends BaseController {

    private final IRoomManager roomManager;

    public RoomController(IRoomManager roomManager) {
        this.roomManager = roomManager;
    }

    /**
     * 添加房间
     *
     * @param roomRequest 房间参数
     * @return 房间
     */
    @PostMapping(value = "save")
    @ApiOperation(value = "添加房间")
    public Result<?> save(@RequestBody @Validated RoomRequest roomRequest) {
        roomManager.save(roomRequest, super.getUserNo());
        return Result.success();
    }

    /**
     * 房间列表
     *
     * @param status 状态查询
     * @return 房间列表
     */
    @GetMapping(value = "list")
    @ApiOperation(value = "房间列表")
    public List<RoomResponse> list(@RequestParam(value = "status", required = false) String status) {
        return roomManager.list(status);
    }


    /**
     * 修改房间状态
     *
     * @param id     房间id
     * @param status 状态
     */
    @GetMapping(value = "update/status/{id}/{status}")
    @ApiOperation(value = "修改房间状态 ")
    public void post(@PathVariable(value = "id") @NotNull(message = "id不能为空") Long id,
                     @PathVariable(value = "status") @NotNull(message = "status不能为空") String status) {
        roomManager.updateStatus(id, status, super.getUserNo());
    }

    /**
     * 退房
     *
     * @param id 房间id
     */
    @GetMapping(value = "quit/{id}")
    @ApiModelProperty(value = "退房")
    public void quit(@PathVariable(value = "id") Long id) {
        roomManager.quit(id, super.getUserNo());
    }

    /**
     * 退房/改价
     *
     * @param roomSwapRequest 参数
     */
    @GetMapping(value = "swap")
    @ApiModelProperty(value = "退房/改价")
    public void swap(@RequestBody RoomSwapRequest roomSwapRequest) {
        roomManager.swap(roomSwapRequest, super.getUserNo());
    }
}
