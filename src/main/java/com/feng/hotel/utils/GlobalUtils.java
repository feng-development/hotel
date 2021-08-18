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


import com.feng.hotel.base.Constants;
import com.feng.hotel.base.entity.response.CommonResult;
import com.feng.hotel.base.entity.response.Pagination;
import com.feng.hotel.base.entity.response.Result;
import com.feng.hotel.base.exception.BizException;
import com.feng.hotel.base.exception.BizInfo;

import com.feng.hotel.base.exception.ExceptionWrapper;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 全局工具类
 *
 * @author asheng
 * @since 2019/3/26
 */
public final class GlobalUtils {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalUtils.class);

    private GlobalUtils() {
    }

    /**
     * 判断返回结果是否正确
     *
     * @param result {@link Result}
     * @return true: 处理成功  false: 处理失败
     */
    public static <T> boolean isSuccess(Result<T> result) {
        return result != null && Objects.equals(BizInfo.SUCCESS.code(), result.getStatus());
    }

    /**
     * 判断返回结果是否正确
     *
     * @param result {@link Result}
     * @return true: 处理成功  false: 处理失败
     */
    public static <T> boolean isSuccess(CommonResult<T> result) {
        return result != null && BizInfo.SUCCESS.code() == result.getCode();
    }

    /**
     * 查看输入号码是否是正确的格式
     *
     * @param telephone 手机号码
     * @return true：正确的手机号码 false：手机号码错误
     */
    public static boolean isMobile(String telephone) {
        if (StringUtils.isEmpty(telephone)) {
            return false;
        }
        Matcher matcher = Constants.MOBILE_PATTERN.matcher(telephone);
        return matcher.matches();
    }

    /**
     * 判断Http调用是否成功
     *
     * @param code Http状态吗
     * @return {@code true}调用成功 {@code false}调用失败
     */
    public static boolean isHttpOk(int code) {
        return Objects.equals(code, HttpStatus.SC_OK);
    }

    /**
     * 查看需要匹配的字符串是否在list之中
     *
     * @param matchStr    需要匹配的字符串
     * @param matchedList 匹配库
     * @return {@code true} 数组中包含 {@code false} 数组中不包含
     */
    public static boolean isAnyMatch(String matchStr, Collection<String> matchedList) {
        return !StringUtils.isEmpty(matchStr) && !(matchedList == null || matchedList.isEmpty())
            && matchedList.contains(matchStr);
    }

    /**
     * 查看需要匹配的字符串是否在list之中
     *
     * @param matchStr     需要匹配的字符串
     * @param matchedArray 匹配库
     * @return {@code true} 数组中包含 {@code false} 数组中不包含
     */
    public static boolean isAnyMatch(String matchStr, String... matchedArray) {
        return matchedArray != null && matchedArray.length != 0 && isAnyMatch(matchStr,
            Arrays.asList(matchedArray));
    }

    /**
     * 判断对象是否包含在匹配数组中 Note:匹配的是equals()方法!
     *
     * @param match        需要匹配的对象
     * @param matchedArray 匹配数组
     * @return {@code true} 数组中包含 {@code false} 数组中不包含
     */
    public static boolean isAnyMatch(Object match, Object... matchedArray) {
        return !isNull(matchedArray) && Arrays.asList(matchedArray).contains(match);
    }

    /**
     * 查看需要匹配的字符串是否在list之中，不考虑大小写
     *
     * @param matchStr     需要匹配的字符串
     * @param matchedArray 匹配库
     * @return {@code true} 数组中包含 {@code false} 数组中不包含
     */
    public static boolean isAnyMatchIgnoreCase(String matchStr, String... matchedArray) {
        if (isNull(matchedArray)) {
            return false;
        }

        if (StringUtils.isEmpty(matchStr)) {
            return false;
        }

        String[] lowerCaseMatchedArray = new String[matchedArray.length];
        for (int i = 0; i < matchedArray.length; i++) {
            lowerCaseMatchedArray[i] = matchedArray[i].toLowerCase();
        }
        return isAnyMatch(matchStr.toLowerCase(), lowerCaseMatchedArray);
    }

    /**
     * 生成验证码
     *
     * @return 验证码
     */
    public static String generateVerifyCode() {
        return generateNumber(6);
    }

    /**
     * 随机生成字母和数字
     *
     * @param length 长度
     * @return 长度为length的字母和数字
     */
    public static String generateAlphaNumber(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    /**
     * 随机生成字母
     *
     * @param length 长度
     * @return 长度为length的字母
     */
    public static String generateAlphabetic(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    /**
     * 随机生成数组
     *
     * @param length 长度
     * @return 长度为length的数字
     */
    public static String generateNumber(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    /**
     * 判断是数组否为空
     *
     * @param array 数组
     * @return true为空 false不为空
     */
    public static boolean isNull(Object[] array) {
        return Objects.isNull(array) || array.length == 0;
    }

    /**
     * 判断是否有空的字符串
     *
     * @param array 字符串数组
     * @return true:包含空的字符串  false:不包含
     */
    public static boolean isAnyBlank(String... array) {
        if (Objects.isNull(array)) {
            return false;
        }

        List<String> collect = Arrays.stream(array)
            .filter(StringUtils::isEmpty)
            .collect(Collectors.toList());
        return !collect.isEmpty();
    }

    /**
     * 字节数组转String字串
     *
     * @param bytes 字节数组
     * @return String字串
     */
    public static String byteArrayToString(byte[] bytes) {
        return new String(bytes, Constants.UTF_8_CHARSET);
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName 文件名，可以是物理磁盘上的文件名，也可以是url文件名
     * @return 扩展名
     */
    public static String getFileExt(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return StringUtils.EMPTY;
        }

        if (fileName.contains(Constants.DOT)) {
            return fileName.substring(fileName.lastIndexOf(Constants.DOT));
        }

        return StringUtils.EMPTY;
    }


    /**
     * 对Result分页对象解包装
     *
     * @param result 返回结果
     * @param <T>    返回类型
     * @return 当前分页里的列表数据
     */
    public static <T> List<T> unwrapPage(Result<Pagination<T>> result) {
        Pagination<T> page = unwrap(result);
        if (page == null || page.isEmpty() || page.getResult() == null || page.getResult()
            .isEmpty()) {
            return Collections.emptyList();
        }
        return page.getResult();
    }

    /**
     * 对Result对象解包装
     *
     * @param result 返回结果
     * @param <T>    返回类型
     * @return 当前结果
     */
    public static <T> T unwrap(Result<T> result) {
        if (!GlobalUtils.isSuccess(result)) {
            BizException wrap = ExceptionWrapper.wrap(result);
            LOG.error("unwrap result error. detail message: [{}]", wrap.getDetailMessage());
            throw wrap;
        }
        return result.getData();
    }

    /**
     * 对Result对象解包装
     *
     * @param commonResult 返回结果
     * @param <T>          返回类型
     * @return 当前结果
     */
    public static <T> T unwrap(CommonResult<T> commonResult) {
        if (!GlobalUtils.isSuccess(commonResult)) {
            BizException wrap = ExceptionWrapper.wrap(commonResult);
            LOG.error("unwrap result error. detail message: [{}]", wrap.getDetailMessage());
            throw wrap;
        }
        return commonResult.getData();
    }

}
