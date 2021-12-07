package com.feng.hotel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.hotel.domain.PayRecord;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 支付记录 服务类
 * </p>
 *
 * @author feng
 * @since 2021-12-07
 */
public interface IPayRecordService extends IService<PayRecord> {

    /**
     * 创建首款记录
     *
     * @param orderId 订单id
     * @param price   价钱
     * @param userNo  操作人
     */
    void save(Long orderId, int price, Long userNo);

    /**
     * 查询付款记录
     *
     * @param orderIds 订单id
     * @return 付款记录
     */
    List<PayRecord> queryByOrderIds(Set<Long> orderIds);
}
