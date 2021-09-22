package com.feng.hotel.request;

import com.feng.hotel.base.entity.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author feng
 * @since 2021/9/22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerPageQuery extends PageRequest {


    /**
     * 身份证
     */
    private String idNo;

    /**
     * 姓名
     */
    private String name;
}
