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

import java.util.Arrays;

/**
 * @author Administrator
 * @since 2021/7/19
 */
public enum EnumReturnStatus {
    /**
     * 默认返回
     */
    SUCCESS("200",                                      "sucess"),
    ROOM_STATUS_ERROR("201",                            "房间状态错误"),
    ROOM_NOT_EXIST("202",                               "房间不存在");

    private String status;
    private String msg;

    EnumReturnStatus(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static EnumReturnStatus getByStatus(String status) {
        return Arrays.stream(EnumReturnStatus.values())
            .filter(x -> x.status.equals(status))
            .findFirst().orElse(null);
    }
}
