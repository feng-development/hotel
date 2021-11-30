package com.feng.hotel.common;

import com.feng.hotel.base.Constants;

/**
 * @author feng
 * @since 2021/8/7
 */
public interface HotelConstants extends Constants {

    interface OrderStatus {
        /**
         * 入住中
         */
        String LODGING = "lodging";


        /**
         * 退房
         */
        String OUT = "out";

        /**
         * 关闭
         */
        String CLOSE = "close";
    }

    /**
     * 添加
     */
    interface Insert {
    }


    /**
     * 修改
     */
    interface Update {

    }

    ;

}
