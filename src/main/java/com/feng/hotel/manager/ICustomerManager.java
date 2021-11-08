package com.feng.hotel.manager;

import com.feng.hotel.base.Pagination;
import com.feng.hotel.request.CustomerPageQuery;
import com.feng.hotel.response.CustomerResponse;

/**
 * @author feng
 * @since 2021/9/22
 */
public interface ICustomerManager {
    /**
     * 客户分页查询
     *
     * @param customerPageQuery 客户查询参数
     * @return
     */
    Pagination<CustomerResponse> page(CustomerPageQuery customerPageQuery);
}
