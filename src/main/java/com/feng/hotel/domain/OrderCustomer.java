package com.feng.hotel.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单客户
 * </p>
 *
 * @author evision
 * @since 2021-08-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_order_customer")
public class OrderCustomer extends Model<OrderCustomer> {

    private static final long serialVersionUID = 1L;

    /**
     * 创建人
     */
    private Long creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private Long modifier;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 是否删除 0删除
     */
    private Integer valid;

    /**
     * id
     */
    private Long id;

    /**
     * 房间id
     */
    private Long roomId;

    /**
     * 订单id
     */
    private Long orderId;
    /**
     * 订单状态 lodging：入住中，out离开，closure:关闭
     */
    private String status;
    /**
     * 客户id
     */
    private Long customerId;


    public static final String CREATOR = "creator";

    public static final String CREATE_TIME = "create_time";

    public static final String MODIFIER = "modifier";

    public static final String MODIFY_TIME = "modify_time";

    public static final String VALID = "valid";

    public static final String ID = "id";

    public static final String ROOM_ID = "room_id";

    public static final String ORDER_ID = "order_id";

    public static final String CUSTOMER_ID = "customer_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
