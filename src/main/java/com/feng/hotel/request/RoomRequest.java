package com.feng.hotel.request;

import com.feng.hotel.common.HotelConstants;
import com.feng.hotel.common.enums.RoomStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Administrator
 * @since 2021/8/3
 */
@Data
public class RoomRequest {

    @ApiModelProperty(value = "id")
    @NotNull(message = "id 不能为空", groups = HotelConstants.Insert.class)
    private Long id;

    @ApiModelProperty(value = "房间号")
    @NotBlank(message = "房间号 不能为空", groups = {HotelConstants.Insert.class, HotelConstants.Update.class})
    @Length(max = 32, message = "房间号 长度最大为32个字符", groups = {HotelConstants.Insert.class, HotelConstants.Update.class})
    private String roomNo;


    @ApiModelProperty(value = "价格 分")
    @NotNull(message = "价格 不能为空", groups = {HotelConstants.Insert.class, HotelConstants.Update.class})
    @Min(value = 0, message = "价格 不能小于0", groups = {HotelConstants.Insert.class, HotelConstants.Update.class})
    private Integer price;


    @ApiModelProperty(value = "状态 normal:正常，ready_clean：打扫中，repair：维修中，quit：退房")
    private String type = RoomStatusEnum.NORMAL.name();


}
