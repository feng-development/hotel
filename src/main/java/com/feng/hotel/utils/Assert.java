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


import com.feng.hotel.base.exception.BizException;
import com.feng.hotel.base.exception.BizInfo;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 断言[自定义，本项目使用此断言进行参数处理]
 */
public final class Assert {

    private Assert() {
    }

    /**
     * 对象不能为空
     *
     * @param obj     判断对象
     * @param message 错误信息
     */
    public static void assertNotNull(Object obj, String message) {
        if (obj == null) {
            throw new BizException(BizInfo.PARAM_VALIDATE_ERROR, message);
        }
    }

    /**
     * 对象不能为空
     *
     * @param obj     判断对象
     * @param bizInfo 错误枚举
     */
    public static void assertNotNull(Object obj, BizInfo bizInfo) {
        if (obj == null) {
            throw new BizException(bizInfo);
        }
    }

    /**
     * 字符串不能为空
     *
     * @param obj     判断对象
     * @param message 错误信息
     */
    public static void assertNotEmpty(String obj, String message) {
        if (StringUtils.isEmpty(obj)) {
            throw new BizException(BizInfo.PARAM_VALIDATE_ERROR, message);
        }
    }

    /**
     * 字符串不能为空
     *
     * @param obj     判断对象
     * @param message 错误信息
     */
    public static void assertNotBlank(String obj, String message) {
        if (StringUtils.isBlank(obj)) {
            throw new BizException(BizInfo.PARAM_VALIDATE_ERROR, message);
        }
    }

    /**
     * 字符串不能为空
     *
     * @param obj     判断对象
     * @param bizInfo 错误枚举
     */
    public static void assertNotBlank(String obj, BizInfo bizInfo) {
        if (StringUtils.isBlank(obj)) {
            throw new BizException(bizInfo);
        }
    }

    /**
     * 集合不能为空
     *
     * @param collection 集合
     * @param message    错误信息
     */
    public static <T> void assertNotEmpty(Collection<T> collection, String message) {
        if (collection == null || collection.isEmpty()) {
            throw new BizException(BizInfo.PARAM_VALIDATE_ERROR, message);
        }
    }

    /**
     * 集合不能为空
     *
     * @param collection 集合
     * @param bizInfo    错误信息
     */
    public static <T> void assertNotEmpty(Collection<T> collection, BizInfo bizInfo) {
        if (collection == null || collection.isEmpty()) {
            throw new BizException(bizInfo);
        }
    }

    /**
     * map不能为空
     *
     * @param map     map对象
     * @param message 错误信息
     */
    public static <K, V> void assertNotEmpty(Map<K, V> map, String message) {
        if (map == null || map.isEmpty()) {
            throw new BizException(BizInfo.PARAM_VALIDATE_ERROR, message);
        }
    }

    /**
     * map不能为空
     *
     * @param map     map对象
     * @param bizInfo 错误信息
     */
    public static <K, V> void assertNotEmpty(Map<K, V> map, BizInfo bizInfo) {
        if (map == null || map.isEmpty()) {
            throw new BizException(bizInfo);
        }
    }

    /**
     * 输入的值不能小于等于比较的值
     *
     * @param value   输入值
     * @param compare 比较值
     * @param message 错误信息
     */
    public static void assertNotLE(long value, long compare, String message) {
        if (value <= compare) {
            throw new BizException(BizInfo.PARAM_VALIDATE_ERROR, message);
        }
    }

    /**
     * 输入的值不能小于等于比较的值
     *
     * @param value   输入值
     * @param compare 比较值
     * @param bizInfo 错误信息
     */
    public static void assertNotLE(long value, long compare, BizInfo bizInfo) {
        if (value <= compare) {
            throw new BizException(bizInfo);
        }
    }

    /**
     * 输入的值不能小于比较的值
     *
     * @param value   输入值
     * @param compare 比较值
     * @param message 错误信息
     */
    public static void assertNotLT(long value, long compare, String message) {
        if (value < compare) {
            throw new BizException(BizInfo.PARAM_VALIDATE_ERROR, message);
        }
    }

    /**
     * 输入的值不能小于比较的值
     *
     * @param value   输入值
     * @param compare 比较值
     * @param bizInfo 错误信息
     */
    public static void assertNotLT(long value, long compare, BizInfo bizInfo) {
        if (value < compare) {
            throw new BizException(bizInfo);
        }
    }

    /**
     * 输入的值不能大于等于比较的值
     *
     * @param value   输入值
     * @param compare 比较值
     * @param message 错误信息
     */
    public static void assertNotME(long value, long compare, String message) {
        if (value >= compare) {
            throw new BizException(BizInfo.PARAM_VALIDATE_ERROR, message);
        }
    }

    /**
     * 输入的值不能大于等于比较的值
     *
     * @param value   输入值
     * @param compare 比较值
     * @param bizInfo 错误信息
     */
    public static void assertNotME(long value, long compare, BizInfo bizInfo) {
        if (value >= compare) {
            throw new BizException(bizInfo);
        }
    }

    /**
     * 输入的值不能大于比较的值
     *
     * @param value   输入值
     * @param compare 比较值
     * @param message 错误信息
     */
    public static void assertNotMT(long value, long compare, String message) {
        if (value > compare) {
            throw new BizException(BizInfo.PARAM_VALIDATE_ERROR, message);
        }
    }

    /**
     * 输入的值不能大于比较的值
     *
     * @param value   输入值
     * @param compare 比较值
     * @param bizInfo 错误信息
     */
    public static void assertNotMT(long value, long compare, BizInfo bizInfo) {
        if (value > compare) {
            throw new BizException(bizInfo);
        }
    }
}
