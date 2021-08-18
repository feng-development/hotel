package com.feng.hotel.common.enums;

import com.feng.hotel.base.exception.BizInfo;
import lombok.Getter;

/**
 * @author Administrator
 * @since 2021/8/3
 */

public enum HotelEnum implements BizInfo {
    //----------------------用户相关-----------------------------
    AUTH_ERROR("4001", "账号或密码错误"),


    //----------------------用户相关-----------------------------
    REVERSED_SIDE_ERROR("4101", "身份证未摆正"),
    NON_ID_CARD_ERROR("4102", "图片错误"),
    BLURRED("4103", "图片模糊"),
    OVER_EXPOSURE("4104", "图片反光"),
    UNKNOWN("4105", "未知错误"),

    ROOM_STATUS_ERROR("4201", "房间状态错误"),
    ROOM_NOT_EXIST_ERROR("4202", "房间不存在"),

    ;
    private final String code;

    private final String msg;

    HotelEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String msg() {
        return this.msg;
    }
}
