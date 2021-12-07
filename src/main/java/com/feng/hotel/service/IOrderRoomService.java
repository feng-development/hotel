package com.feng.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.hotel.domain.OrderRoom;
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
     * @param request 房间信息
     * @param userNo  用户id
     * @return 订单房间关联
     */
    OrderRoom save(Long orderId, RoomUserRequest request, Long userNo);

    /**
     * 查询订单房间
     *
     * @param roomIds 房间id
     * @param status  房间状态
     */
    List<OrderRoom> query(Set<Long> roomIds, String status);

    /**
     * 根据订单查询
     *
     * @param orderIds 订单id
     * @return 订单房间管理表
     */
    List<OrderRoom> queryByOrderIds(Set<Long> orderIds);

    /**
     * 修改状态
     *
     * @param orderId         订单id
     * @param roomId          房间id
     * @param orderRoomStatus 订单
     * @param userNo          用户id
     */
    void updateStatus(Long orderId, Long roomId, String orderRoomStatus, Long userNo);

    /**
     * 添加房间
     *
     * @param orderId 订单id
     * @param pid     上级id
     * @param type    类型
     * @param price   价格
     * @param roomId  房间id
     * @param userNo  当前登陆人
     * @return 房间信息
     */
    OrderRoom save(Long orderId, Long pid, String type, Integer price, Long roomId, Long userNo);
}
