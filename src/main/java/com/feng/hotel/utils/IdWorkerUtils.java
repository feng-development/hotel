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
package com.feng.hotel.utils;

import java.util.UUID;

/**
 * @author asheng
 * @since 2019/1/9
 */
public final class IdWorkerUtils {

    private IdWorkerUtils() {
    }

    /**
     * 生成UUID的标识
     *
     * @return UUID
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成long类型的id
     *
     * @return Long类型
     */
    public static Long generateLongId() {
        return SnowFlake.nextId();
    }

    /**
     * 生成以Long类型为基础的String类型id
     *
     * @return String类型
     */
    public static String generateStringId() {
        return String.valueOf(generateLongId());
    }

}
