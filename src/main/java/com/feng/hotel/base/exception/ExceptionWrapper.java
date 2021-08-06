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
package com.feng.hotel.base.exception;

import com.alibaba.fastjson.JSONObject;
import com.feng.hotel.base.entity.response.CommonResult;
import com.feng.hotel.base.entity.response.Result;
import java.util.Objects;

/**
 * 异常包装器
 *
 * @author Administrator
 * @since 2021/7/14 17:14
 */
public class ExceptionWrapper {

    /**
     * 将异常处理成为业务异常
     *
     * @param cause 异常原因
     * @return 业务异常
     */
    public static BizException wrap(Throwable cause) {
        if (cause instanceof BizException) {
            return (BizException) cause;
        }

        return new BizException(BizInfo.SYS_ERROR);
    }

    /**
     * 将BizInfo转成业务异常
     *
     * @param bizInfo 业务信息
     * @return 业务异常
     */
    public static BizException wrap(BizInfo bizInfo) {

        if (Objects.equals(bizInfo.code(), BizInfo.SUCCESS.code())) {
            throw new RuntimeException(
                "biz code with '" + bizInfo.code()
                    + "' is not allow wrap to BizException. detail biz info: "
                    + JSONObject.toJSONString(bizInfo));
        }
        return new BizException(bizInfo);
    }

    /**
     * 将Result对象转换成业务异常
     *
     * @param result {@link Result}
     * @return 业务异常
     */
    public static <T> BizException wrap(Result<T> result) {
        String code = result.getStatus();
        if (Objects.equals(code, BizInfo.SUCCESS.code())) {
            throw new RuntimeException(
                "biz code with '" + code
                    + "' is not allow wrap to BizException. detail result info: "
                    + JSONObject.toJSONString(result));
        }
        return new BizException(code, result.getMsg());
    }

    /**
     * 将Result对象转换成业务异常
     *
     * @param commonResult {@link Result}
     * @return 业务异常
     */
    public static <T> BizException wrap(CommonResult<T> commonResult) {
        String code = commonResult.getCode();
        if (Objects.equals(code, BizInfo.SUCCESS.code())) {
            throw new RuntimeException(
                "biz code with '" + code
                    + "' is not allow wrap to BizException. detail result info: "
                    + JSONObject.toJSONString(commonResult));
        }
        return new BizException(commonResult.getCode(), commonResult.getMessage());
    }
}
