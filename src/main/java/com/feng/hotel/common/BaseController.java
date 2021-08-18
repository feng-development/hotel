/*
 *  Copyright 1999-2019 Evision Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.feng.hotel.common;

import com.alibaba.druid.util.FnvHash.Constants;
import com.feng.hotel.base.exception.BizException;
import com.feng.hotel.common.enums.BizCodeEnum;
import com.feng.hotel.utils.JwtBean;
import com.feng.hotel.utils.RequestContextUtils;

import java.util.Objects;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

/**
 * @author asheng
 * @since 2019/10/11
 */
public class BaseController {

    @Resource
    protected HttpServletRequest request;

    @Resource
    protected HttpServletResponse response;


    /**
     * 获取用户id
     *
     * @return 用户id
     */
    protected Long getUserNo() {
        Long userNo = RequestContextUtils.getUserNo();
        if (Objects.isNull(userNo)) {
            throw new BizException(BizCodeEnum.ACQUIRE_AUTH_TOKEN_ERROR);
        }
        return userNo;

    }


}
