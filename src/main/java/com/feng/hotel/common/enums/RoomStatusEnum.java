package com.feng.hotel.common.enums;

import lombok.Getter;

/**
 * 房间状态枚举
 *
 * @author Administrator
 * @since 2021/8/3
 */
@Getter
public enum RoomStatusEnum {

    /**
     * 正常
     */
    NORMAL,
    /**
     * 打扫中
     */
    READY_CLEAN,
    /**
     * 维修中
     */
    REPAIR;
}
