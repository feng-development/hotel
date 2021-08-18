package com.feng.hotel.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.feng.hotel.base.Constants.Valid;
import com.feng.hotel.domain.Customer;
import com.feng.hotel.mapper.CustomerMapper;
import com.feng.hotel.response.IdCardResult;
import com.feng.hotel.service.ICustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.hotel.utils.CollectionUtils;
import com.feng.hotel.utils.IdWorkerUtils;
import com.feng.hotel.utils.Limit;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户  服务实现类
 * </p>
 *
 * @author evision
 * @since 2021-08-09
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements
    ICustomerService {

    @Override
    public Customer trySave(IdCardResult idCardResult, String path) {
        Customer customer = this.getByIdNum(idCardResult);
        if (Objects.nonNull(customer)) {
            return customer;
        }
        customer = new Customer()
            .setId(IdWorkerUtils.generateLongId())
            .setName(idCardResult.getName())
            .setIdNo(idCardResult.getIdNum())
            .setIdUrl(path);

        this.save(customer);

        return customer;
    }

    @Override
    public List<Customer> queryByIds(Set<Long> customerIds) {
        if (CollectionUtils.isEmpty(customerIds)) {
            return Collections.emptyList();
        }
        return this.list(
            Wrappers.<Customer>lambdaQuery()
                .in(Customer::getId)
                .eq(Customer::getValid, Valid.NORMAL)
        );
    }

    private Customer getByIdNum(IdCardResult idCardResult) {
        return this.getOne(
            Wrappers.<Customer>lambdaQuery()
                .eq(Customer::getIdNo, idCardResult.getIdNum())
                .last(Limit.lastOne())
        );
    }

}
