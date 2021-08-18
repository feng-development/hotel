package com.feng.hotel.common.enums;

import com.feng.hotel.base.exception.BizException;
import com.feng.hotel.base.exception.BizInfo;
import com.feng.hotel.utils.Assert;
import com.feng.hotel.utils.GlobalUtils;

import java.util.Arrays;
import java.util.Objects;

import org.apache.ibatis.jdbc.Null;
import org.apache.tomcat.jni.Global;

/**
 * @author Administrator
 * @since 2021/8/9
 */
public enum IdCardStatusEnum {
    /**
     * 正常
     */
    NORMAL("normal", null),
    /**
     * 身份证未摆正
     */
    REVERSED_SIDE("reversed_side", HotelEnum.REVERSED_SIDE_ERROR),
    /**
     * 图片错误
     */
    NON_ID_CARD("non_idcard", HotelEnum.NON_ID_CARD_ERROR),
    /**
     * 图片模糊
     */
    BLURRED("blurred", HotelEnum.BLURRED),
    /**
     * 图片反光
     */
    OVER_EXPOSURE("over_exposure", HotelEnum.OVER_EXPOSURE),

    UNKNOWN("unknown", HotelEnum.UNKNOWN);
    /**
     * 百度返回状态码
     */
    private final String code;

    /**
     * 对应的错误异常
     */
    private final BizInfo bizInfo;

    IdCardStatusEnum(String code, BizInfo bizInfo) {
        this.code = code;
        this.bizInfo = bizInfo;
    }

    /**
     * 验证当前状态是否是正常
     *
     * @param code 枚举名
     */
    public static void check(String code) {
        IdCardStatusEnum idCardStatusEnum = Arrays.stream(IdCardStatusEnum.values())
            .filter(e -> Objects.equals(e.getCode(), code)).findAny().orElse(null);

        Assert.assertNotNull(idCardStatusEnum, BizInfo.SYS_ERROR);

        BizInfo bizInfo = idCardStatusEnum.getBizInfo();
        if (Objects.nonNull(bizInfo)) {
            throw new BizException(bizInfo);
        }
    }

    public String getCode() {
        return code;
    }

    public BizInfo getBizInfo() {
        return bizInfo;
    }
}
