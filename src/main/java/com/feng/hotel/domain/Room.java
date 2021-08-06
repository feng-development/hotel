package com.feng.hotel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 房间 
 * </p>
 *
 * @author evision
 * @since 2021-08-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_room")
public class Room extends Model<Room> {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    private Long id;

    /**
     * 房间号
     */
    private String roomNo;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 状态 normal:正常，ready_clean：打扫中，repair：维修中，quit：退房
     */
    private String status;

    /**
     * 当前入住订单id 退房时清除
     */
    private String orderId;

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




    public static final String ROOM_NO = "room_no";

    public static final String PRICE = "price";

    public static final String STATUS = "status";

    public static final String ORDER_ID = "order_id";

    public static final String CREATOR = "creator";

    public static final String CREATE_TIME = "create_time";

    public static final String MODIFIER = "modifier";

    public static final String MODIFY_TIME = "modify_time";

    public static final String VALID = "valid";

    public static final String ID = "id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
