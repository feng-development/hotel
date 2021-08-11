package com.feng.hotel.service;

import com.feng.hotel.domain.Customer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.hotel.response.IdCardResult;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 客户  服务类
 * </p>
 *
 * @author evision
 * @since 2021-08-09
 */
public interface ICustomerService extends IService<Customer> {

  /**
   * 尝试添加客户 身份证存在返回原来的数据
   *
   * @param idCardResult 客户信息
   * @param path         图片路径
   */
  Customer trySave(IdCardResult idCardResult, String path);

  /**
   * 根据id查询客户
   *
   * @param customerIds 客户id
   * @return
   */
  List<Customer> queryByIds(Set<Long> customerIds);
}
