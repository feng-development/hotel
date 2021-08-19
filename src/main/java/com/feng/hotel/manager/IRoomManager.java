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
    void save(RoomRequest roomRequest,Long userNo);

    /**
     * 获取房间列表
     *
     * @param status 房间状态
     * @return 房间列表
     */
    List<RoomResponse> list(String status);

    /**
     * 修改房间状态
     *
     * @param id     房间id
     * @param status 状态
     * @param userId 当前登陆人id
     */
    void updateStatus(Long id, String status, Long userId);

    /**
     * 退房
     *
     * @param roomId     房间id
     * @param userNo 操作人
     */
    void quit(Long roomId, Long userNo);

}
