package com.feng.hotel.manager.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.feng.hotel.domain.Customer;
import com.feng.hotel.manager.ICustomerManager;
import com.feng.hotel.request.CustomerPageQuery;
import com.feng.hotel.service.ICustomerService;
import org.springframework.stereotype.Service;

/**
 * @author feng
 * @since 2021/9/22
 */
@Service
public class CustomerManagerImpl implements ICustomerManager {

    private final ICustomerService customerService;

    public CustomerManagerImpl(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void page(CustomerPageQuery customerPageQuery) {
        IPage<Customer> page = customerService.page(customerPageQuery);


    }
}
