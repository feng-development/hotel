package com.feng.hotel.response;

import com.feng.hotel.annotation.IdField;
import com.feng.hotel.annotation.ParentIdField;
import com.feng.hotel.utils.tree.AbstractTreeNode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author feng
 * @since 2021/12/7
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderRoomTreeResponse extends AbstractTreeNode<OrderRoomTreeResponse> {

    /**
     * id
     */
    @IdField
    private Long id;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 房间id
     */
    private Long roomId;

    /**
     * 上间房子的订单房间id 仅做转房使用
     */
    @ParentIdField
    private Long pid;
    /**
     * 订单状态 lodging：入住中，out离开，closure:关闭
     */
    private String status;

    /**
     * 房间价格
     */
    private Integer price;
    /**
     * 入住时间
     */
    private Date beginTime;

    /**
     * 离开时间
     */
    private Date engTime;

    /**
     * 订单类型 钟点房 日租  月租
     */
    private String type;
    /**
     * 创建人
     */
    private Long creator;
}
