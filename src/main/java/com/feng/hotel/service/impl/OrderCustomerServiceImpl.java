package com.feng.hotel.service.impl;

import com.feng.hotel.base.Constants.Valid;
import com.feng.hotel.domain.OrderCustomer;
import com.feng.hotel.mapper.OrderCustomerMapper;
import com.feng.hotel.service.IOrderCustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.hotel.utils.IdWorkerUtils;
import java.util.Date;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单客户  服务实现类
 * </p>
 *
 * @author evision
 * @since 2021-08-09
 */
@Service
public class OrderCustomerServiceImpl extends
    ServiceImpl<OrderCustomerMapper, OrderCustomer> implements IOrderCustomerService {

  @Override
  public void save(Long roomId, Long orderId, Long customerId) {
    Date date = new Date();
    this.save(
        new OrderCustomer()
            .setId(IdWorkerUtils.generateLongId())
            .setRoomId(roomId)
            .setCustomerId(customerId)
            .setOrderId(orderId)
            .setValid(Valid.NORMAL)
            .setCreateTime(date)
            .setModifyTime(date)
    );
  }
}
