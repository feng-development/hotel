package com.feng.hotel.manager;

import com.feng.hotel.HotelApplicationTests;
import com.feng.hotel.common.enums.OrderRoomTypeEnum;
import com.feng.hotel.request.RoomRequest;
import com.feng.hotel.response.RoomResponse;
import com.feng.hotel.utils.IdWorkerUtils;
import com.feng.hotel.utils.json.JsonUtils;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Administrator
 * @since 2021/8/3
 */
public class IRoomManagerTest extends HotelApplicationTests {

    @Autowired
    private IRoomManager roomManager;

    @Test
    public void save() {
        RoomRequest roomRequest = new RoomRequest();
        roomRequest.setRoomNo("401");
        roomRequest.setPrice(new BigDecimal("60"));
        roomRequest.setId(IdWorkerUtils.generateLongId());
        roomRequest.setType("大床");
        roomManager.save(roomRequest,123L);

        System.out.println(JsonUtils.serialize(roomRequest));
    }

    @Test
    public void list() {
        List<RoomResponse> list = roomManager.list(null);
        System.out.println(JsonUtils.serialize(list));
    }

    @Test
    public void name() {
        roomManager.quit(123L,123L);
    }
}