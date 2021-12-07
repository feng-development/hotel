package com.feng.hotel.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 客户
 * </p>
 *
 * @author evision
 * @since 2021-08-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_customer")
public class Customer extends Model<Customer> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 身份证
     */
    private String idNo;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String cex;

    /**
     * 身份证图片
     */
    private String idUrl;

    /**
     * 地址
     */
    private String addr;

    /**
     * 出生日期
     */
    private String dateBirth;

    /**
     * 民族
     */
    private String nation;

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


    public static final String ID = "id";

    public static final String ID_NO = "id_no";

    public static final String NAME = "name";

    public static final String CEX = "cex";

    public static final String ID_URL = "id_url";

    public static final String CREATOR = "creator";

    public static final String CREATE_TIME = "create_time";

    public static final String MODIFIER = "modifier";

    public static final String MODIFY_TIME = "modify_time";

    public static final String VALID = "valid";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
