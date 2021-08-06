package com.feng.hotel.response;

import com.feng.hotel.request.RoomRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Administrator
 * @since 2021/8/3
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "房间详细")
public class RoomDetailedResponse extends RoomRequest {

    /**
     * 欠费状态
     */
    private Integer arrearsStatus;
}
