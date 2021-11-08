package com.feng.hotel.controller;

import com.feng.hotel.base.Pagination;
import com.feng.hotel.base.entity.response.Result;
import com.feng.hotel.manager.ICustomerManager;
import com.feng.hotel.request.CustomerPageQuery;
import com.feng.hotel.response.CustomerResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author feng
 * @since 2021/9/22
 */
@RestController
@RequestMapping(value = "customer")
public class CustomerController {

    private final ICustomerManager customerManager;

    public CustomerController(ICustomerManager customerManager) {
        this.customerManager = customerManager;
    }

    /**
     * 分页查询客户列表
     *
     * @param customerPageQuery 查询参数
     * @return 分页查询结果
     */
    public Result<Pagination<CustomerResponse>> page(@RequestBody CustomerPageQuery customerPageQuery) {
        Pagination<CustomerResponse> page = customerManager.page(customerPageQuery);
        return Result.success(page);
    }
}
