package com.feng.hotel.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 支付记录
 * </p>
 *
 * @author feng
 * @since 2021-12-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_pay_record")
public class PayRecord extends Model<PayRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 钱数
     */
    private Integer price;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 创建人
     */
    private Long creator;

    /**
     * 修改人
     */
    private Long modifier;

    /**
     * 修改时间
     */
    @TableField("modifyTime")
    private Date modifyTime;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private Date createTime;


    public static final String ID = "id";

    public static final String PRICE = "price";

    public static final String CREATOR = "creator";

    public static final String MODIFIER = "modifier";

    public static final String MODIFYTIME = "modifyTime";

    public static final String CREATETIME = "createTime";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
