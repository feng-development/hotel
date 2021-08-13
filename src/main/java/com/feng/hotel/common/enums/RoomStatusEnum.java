package com.feng.hotel.common.enums;

import com.feng.hotel.base.exception.BizException;
import java.util.Arrays;
import java.util.Objects;
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
    NORMAL{
        @Override
        RoomStatusEnum[] from() {
            return new RoomStatusEnum[]{READY_CLEAN,REPAIR};
        }
    },
    /**
     * 打扫中
     */
    READY_CLEAN{
        @Override
        RoomStatusEnum[] from() {
            return new RoomStatusEnum[]{NORMAL};
        }
    },
    /**
     * 维修中
     */
    REPAIR {
        @Override
        RoomStatusEnum[] from() {
            return new RoomStatusEnum[]{NORMAL,READY_CLEAN,USING};
        }
    },

    /**
     * 使用中
     */
    USING {
        @Override
        RoomStatusEnum[] from() {
            return new RoomStatusEnum[]{NORMAL};
        }
    };

    abstract RoomStatusEnum[] from();

   public void  validateNextStatus(RoomStatusEnum nextRoomStatusEnum){
       RoomStatusEnum[] from = nextRoomStatusEnum.from();
       long count = Arrays.stream(from).filter(e ->
           Objects.equals(e, this)).count();
       if(count <= 0){
         throw new BizException(HotelEnum.ROOM_STATUS_ERROR);
       }
   }
}
