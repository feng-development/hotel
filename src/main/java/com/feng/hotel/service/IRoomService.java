package com.feng.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.hotel.common.enums.RoomStatusEnum;
import com.feng.hotel.domain.Room;

import java.util.List;
import java.util.Set;

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
    void updateStatus(Set<Long> roomIds, RoomStatusEnum status, Long userNo);

    /**
     * 根据id获取房间
     *
     * @param roomIds 房间id
     * @return 房间列表
     */
    List<Room> queryByIds(Set<Long> roomIds);

    /**
     * 使用房间
     *
     * @param roomIds 房间id
     * @param orderId 订单id
     * @param userNo  操作人
     */
    void using(Set<Long> roomIds, Long orderId, Long userNo);


}
