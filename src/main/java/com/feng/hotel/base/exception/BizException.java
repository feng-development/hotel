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

/**
 * 业务异常
 *
 * @author Administrator
 * @since 2021/7/14 17:14
 */
public class BizException extends RuntimeException {

    /**
     * 业务code
     */
    protected String code;

    /**
     * 业务信息
     */
    protected String msg;

    public BizException(BizInfo bizInfo) {
        this(bizInfo.code(), bizInfo.msg());
    }


    public BizException(EnumReturnStatus bizInfo) {
        this(bizInfo.getStatus(), bizInfo.getMsg());
    }

    public BizException(BizInfo bizInfo, String msg) {
        this(bizInfo.code(), msg);
    }

    public BizException(String code, String msg) {
        this(code, msg, null);
    }

    public BizException(String code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.msg = msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public String getDetailMessage() {
        return String.format("[code]: '%d'. [msg]: '%s'", code, msg);
    }
}
