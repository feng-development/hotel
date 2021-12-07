package com.feng.hotel.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.hotel.base.Constants;
import com.feng.hotel.common.enums.RoomStatusEnum;
import com.feng.hotel.domain.OrderRoomCustomer;
import com.feng.hotel.mapper.OrderRoomCustomerMapper;
import com.feng.hotel.service.IOrderRoomCustomerService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单客户  服务实现类
 * </p>
 *
 * @author feng
 * @since 2021-11-30
 */
@Service
public class OrderRoomCustomerServiceImpl extends ServiceImpl<OrderRoomCustomerMapper, OrderRoomCustomer> implements IOrderRoomCustomerService {

    @Override
    public List<OrderRoomCustomer> queryByOrderRoomId(Set<Long> collect) {
        return this.list(
            Wrappers.<OrderRoomCustomer>lambdaQuery()
                .in(OrderRoomCustomer::getOrderRoomId, collect)
                .eq(OrderRoomCustomer::getValid, Constants.Valid.NORMAL)
        );

    }

    @Override
    public void saveBatch(Long orderRoomId, List<Long> customerIds, Long userNo) {
        Date date = new Date();
        this.saveBatch(customerIds.stream().map(e ->
                new OrderRoomCustomer()
                    .setCreator(userNo)
                    .setModifier(userNo)
                    .setValid(Constants.Valid.NORMAL)
                    .setCreateTime(date)
                    .setModifyTime(date)
                    .setOrderRoomId(orderRoomId)
                    .setStatus(RoomStatusEnum.USING.name())
                    .setCustomerId(e)
            ).collect(Collectors.toSet())
        );
    }
}
