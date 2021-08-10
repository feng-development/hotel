package com.feng.hotel.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单
 * </p>
 *
 * @author feng
 * @since 2021-08-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_order")
public class Order extends Model<Order> {

    private static final long serialVersionUID=1L;

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
     * 订单号
     */
    private String orderNo;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 订单状态 lodging：入住中，out离开，closure:关闭
     */
    private String status;

    /**
     * 订单类型 钟点房 日租  月租
     */
    private Integer type;
    /**
     * 总价(已交的钱)
     */
    private BigDecimal totalPrice;

    /**
     * 押金
     */
    private BigDecimal mortgage;


    public static final String CREATOR = "creator";

    public static final String CREATE_TIME = "create_time";

    public static final String MODIFIER = "modifier";

    public static final String MODIFY_TIME = "modify_time";

    public static final String VALID = "valid";

    public static final String ID = "id";

    public static final String ORDER_NO = "order_no";

    public static final String BALANCE = "balance";

    public static final String STATUS = "status";

    public static final String TOTALPRICE = "totalPrice";

    public static final String MORTGAGE = "mortgage";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
