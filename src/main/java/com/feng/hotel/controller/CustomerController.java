package com.feng.hotel.controller;

import com.feng.hotel.manager.ICustomerManager;
import com.feng.hotel.request.CustomerPageQuery;
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

private ICustomerManager customerManager;

    public CustomerController(ICustomerManager customerManager) {
        this.customerManager = customerManager;
    }

    public void page(@RequestBody CustomerPageQuery customerPageQuery){
        customerManager.page(customerPageQuery);
    }
}
