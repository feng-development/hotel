package com.feng.hotel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.feng.hotel.common.enums.RoomStatusEnum;
import com.feng.hotel.domain.Room;
import com.feng.hotel.mapper.RoomMapper;
import com.feng.hotel.service.IRoomService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.hotel.utils.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 房间  服务实现类
 * </p>
 *
 * @author evision
 * @since 2021-08-02
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements IRoomService {

    @Override
    public List<Room> list(String status) {
        LambdaQueryWrapper<Room> wrapper = Wrappers.<Room>lambdaQuery();
        if (Objects.nonNull(status)) {
            wrapper.eq(Room::getStatus, status);
        }

        return this.list(wrapper);
    }

    @Override
    public void updateStatus(Set<Long> roomIds, RoomStatusEnum status, Long userNo) {

        List<Room> rooms = this.queryByIds(roomIds);
        rooms.forEach(e -> RoomStatusEnum.valueOf(e.getStatus()).validateNextStatus(status));

        this.update(Wrappers.<Room>lambdaUpdate()
            .set(Room::getStatus, status)
            .set(Room::getModifyTime, new Date())
            .set(Room::getModifier, userNo)
            .in(Room::getId, roomIds)
        );
    }

    @Override
    public List<Room> queryByIds(Set<Long> roomIds) {
        if (CollectionUtils.isEmpty(roomIds)) {
            return Collections.emptyList();
        }
        return this.list(
            Wrappers.<Room>lambdaQuery()
                .in(Room::getId, roomIds)
        );
    }
}
