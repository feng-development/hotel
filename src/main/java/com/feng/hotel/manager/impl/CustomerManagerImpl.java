package com.feng.hotel.manager.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.feng.hotel.base.Pagination;
import com.feng.hotel.domain.Customer;
import com.feng.hotel.manager.ICustomerManager;
import com.feng.hotel.request.CustomerPageQuery;
import com.feng.hotel.response.CustomerResponse;
import com.feng.hotel.response.IdCardResult;
import com.feng.hotel.service.ICustomerService;
import com.feng.hotel.service.IIdCardService;
import com.feng.hotel.utils.LambdaUtils;
import com.feng.hotel.utils.json.JsonUtils;
import org.springframework.stereotype.Service;

/**
 * @author feng
 * @since 2021/9/22
 */
@Service
public class CustomerManagerImpl implements ICustomerManager {

    private final ICustomerService customerService;
    private final IIdCardService idCardService;

    public CustomerManagerImpl(ICustomerService customerService, IIdCardService idCardService) {
        this.customerService = customerService;
        this.idCardService = idCardService;
    }


    @Override
    public Pagination<CustomerResponse> page(CustomerPageQuery customerPageQuery) {
        IPage<Customer> page = customerService.page(customerPageQuery);
        return LambdaUtils.page(page, e ->
            JsonUtils.convertList(e, CustomerResponse.class)
        );
    }

    @Override
    public Customer save(String canonicalPath) {
        IdCardResult idCardResult = idCardService.idCardRecognition(canonicalPath);
        return customerService.trySave(idCardResult, canonicalPath);
    }
}
