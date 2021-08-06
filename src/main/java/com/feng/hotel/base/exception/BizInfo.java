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
 * 业务信息
 *
 * @author Administrator
 * @since 2021/7/14 17:14
 */
public interface BizInfo {

    /**
     * 业务code, 0为业务成功
     *
     * @return 业务code
     */
    String code();

    /**
     * 业务提示信息，用于业务提示
     *
     * @return 业务提示信息
     */
    String msg();

    /**
     * 操作成功业务信息
     */
    BizInfo SUCCESS = new BizInfo() {
        @Override
        public String code() {
            return "200";
        }

        @Override
        public String msg() {
            return "操作成功";
        }
    };

    /**
     * 系统异常
     */
    BizInfo SYS_ERROR = new BizInfo() {
        @Override
        public String code() {
            return "500";
        }

        @Override
        public String msg() {
            return "系统繁忙，请稍后重试！";
        }
    };

    /**
     * 参数校验失败
     */
    BizInfo PARAM_VALIDATE_ERROR = new BizInfo() {
        @Override
        public String code() {
            return "2";
        }

        @Override
        public String msg() {
            return "参数校验失败";
        }
    };

    /**
     * 文件上传失败
     */
    BizInfo FILE_UPLOAD_ERROR = new BizInfo() {
        @Override
        public String code() {
            return "3";
        }

        @Override
        public String msg() {
            return "文件上传失败，请重试";
        }
    };
}
