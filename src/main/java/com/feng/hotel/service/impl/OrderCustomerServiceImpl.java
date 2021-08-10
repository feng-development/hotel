package com.feng.hotel.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.feng.hotel.base.Constants.Valid;
import com.feng.hotel.domain.OrderCustomer;
import com.feng.hotel.mapper.OrderCustomerMapper;
import com.feng.hotel.service.IOrderCustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.hotel.utils.CollectionUtils;
import com.feng.hotel.utils.IdWorkerUtils;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
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

  @Override
  public List<OrderCustomer> queryByOrderId(Set<String> orderIds) {
    if (CollectionUtils.isEmpty(orderIds)) {
      return Collections.emptyList();
    }
    return this.list(
        Wrappers.<OrderCustomer>lambdaQuery()
            .in(OrderCustomer::getOrderId, orderIds)
    );
  }
}
