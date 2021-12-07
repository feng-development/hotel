package com.feng.hotel.request;

import lombok.Data;

/**
 * 换房
 *
 * @author feng
 * @since 2021/11/29
 */
@Data
public class RoomSwapRequest {


    /**
     * 房间id
     */
    private Long newRootId;


    /**
     * 原来的订单房间关联id
     */
    private Long orderRootId;

    /**
     * 新房间价格
     */
    private Integer price;

    /**
     * 新房间类型  DAILY:日租 MONTHLY：月组 HOURLY：钟点
     */
    private String type;
}
