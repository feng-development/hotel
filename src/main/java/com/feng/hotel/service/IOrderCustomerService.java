package com.feng.hotel.service;

import com.feng.hotel.domain.OrderCustomer;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 订单客户  服务类
 * </p>
 *
 * @author evision
 * @since 2021-08-09
 */
public interface IOrderCustomerService extends IService<OrderCustomer> {

  /**
   * 添加房间客户订单管理
   *
   * @param roomId     房间id
   * @param customerId 客户id
   * @param orderId    订单id
   */
  void save(Long roomId, Long orderId, Long customerId);


  List<OrderCustomer> queryByOrderId(Set<Long> orderIds);
}
