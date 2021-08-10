package com.feng.hotel.service.impl;

import com.feng.hotel.base.Constants;
import com.feng.hotel.common.HotelConstants;
import com.feng.hotel.domain.Order;
import com.feng.hotel.mapper.OrderMapper;
import com.feng.hotel.request.CreateOrderRequest;
import com.feng.hotel.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.hotel.utils.IdWorkerUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

/**
 * <p>
 * 订单  服务实现类
 * </p>
 *
 * @author feng
 * @since 2021-08-07
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

  @Override
  public Order save(CreateOrderRequest request, Long userNo) {
    Date date = new Date();
    Order order = new Order()
        .setId(IdWorkerUtils.generateLongId())
        .setOrderNo(IdWorkerUtils.generateStringId())
        .setCreator(userNo)
        .setModifier(userNo)
        .setCreateTime(date)
        .setModifyTime(date)
        .setValid(Constants.Valid.NORMAL)
        .setMortgage(request.getMortgage())
        .setStatus(HotelConstants.OrderStatus.LODGING)
        .setTotalPrice(request.getTotalPrice())
        .setBalance(request.getTotalPrice().subtract(request.getMortgage()));

    this.save(order);

    return order;
  }
}