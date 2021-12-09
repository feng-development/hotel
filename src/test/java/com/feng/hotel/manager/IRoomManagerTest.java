package com.feng.hotel.manager;

import com.feng.hotel.HotelApplicationTests;
import com.feng.hotel.common.enums.OrderRoomTypeEnum;
import com.feng.hotel.request.RoomRequest;
import com.feng.hotel.request.RoomSwapRequest;
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
        roomRequest.setRoomNo("404");
        roomRequest.setPrice(6000);
        roomRequest.setId(IdWorkerUtils.generateLongId());
        roomRequest.setType("三人间");
        roomManager.save(roomRequest,123L);

        System.out.println(JsonUtils.serialize(roomRequest));
    }

    @Test
    public void list() {
        List<RoomResponse> list = roomManager.list(null);
        System.out.println(JsonUtils.serialize(list));
    }

    @Test
    public void quit() {
        roomManager.quit(123L,123L);
    }

    @Test
    public void swap() {
        RoomSwapRequest roomSwapRequest = new RoomSwapRequest();
        roomSwapRequest.setPrice(8000);
        roomSwapRequest.setType(OrderRoomTypeEnum.DAILY.name());
        roomSwapRequest.setOrderRootId(8157126223357493248L);
        roomSwapRequest.setNewRootId(8156448341568999424L);
        roomManager.swap(roomSwapRequest,123L);

    }
}