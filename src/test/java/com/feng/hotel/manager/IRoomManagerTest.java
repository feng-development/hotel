package com.feng.hotel.manager;

import static org.junit.Assert.*;

import com.feng.hotel.HotelApplicationTests;
import com.feng.hotel.request.RoomRequest;
import com.feng.hotel.response.RoomResponse;
import com.feng.hotel.utils.IdWorkerUtils;
import com.feng.hotel.utils.json.JsonUtils;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

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
        roomRequest.setStatus("1");
        roomRequest.setId(IdWorkerUtils.generateLongId());
        roomManager.save(roomRequest);

        System.out.println(JsonUtils.serialize(roomRequest));
    }

    @Test
    public void list() {
        List<RoomResponse> list = roomManager.list(null);
        System.out.println(JsonUtils.serialize(list));
    }
}