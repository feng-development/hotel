package com.feng.hotel.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.hotel.domain.PayRecord;
import com.feng.hotel.mapper.PayRecordMapper;
import com.feng.hotel.service.IPayRecordService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 支付记录 服务实现类
 * </p>
 *
 * @author feng
 * @since 2021-12-07
 */
@Service
public class PayRecordServiceImpl extends ServiceImpl<PayRecordMapper, PayRecord> implements IPayRecordService {

    @Override
    public void save(Long orderId, int price, Long userNo) {
        Date date = new Date();
        this.save(
            new PayRecord()
                .setOrderId(orderId)
                .setPrice(price)
                .setCreator(userNo)
                .setModifier(userNo)
                .setCreateTime(date)
                .setModifyTime(date)
        );
    }

    @Override
    public List<PayRecord> queryByOrderIds(Set<Long> orderIds) {
       return this.list(Wrappers.<PayRecord>lambdaQuery().in(PayRecord::getOrderId, orderIds));
    }
}
