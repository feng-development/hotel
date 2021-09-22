package com.feng.hotel.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.feng.hotel.domain.Customer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.hotel.request.CustomerPageQuery;
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
     * @return 客户列表
     */
    List<Customer> queryByIds(Set<Long> customerIds);

    /**
     * 分页查询客户参数
     *
     * @param customerPageQuery 客户查询参数
     * @return 客户列表
     */
    IPage<Customer> page(CustomerPageQuery customerPageQuery);
}
