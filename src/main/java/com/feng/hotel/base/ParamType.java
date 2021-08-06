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
package com.feng.hotel.base;


/**
 * 请求参数类型，主要是为了swagger使用
 *
 * @author Administrator
 * @since 2021/7/14 17:14
 */
public interface ParamType {

    /**
     * path类型参数[存放在url中]
     */
    String PATH = "path";

    /**
     * query类型参数
     */
    String QUERY = "query";

    /**
     * body类型参数
     */
    String BODY = "body";

    /**
     * header类型参数
     */
    String HEADER = "header";

    /**
     * form类型参数
     */
    String FORM = "form";

}
