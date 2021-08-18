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
import com.feng.hotel.common.enums.BizCodeEnum;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 获取当前请求HttpServletRequest
 *
 * @author asheng
 * @since 2019/10/12
 */
public final class RequestContextUtils {

    /**
     * HttpServletRequest容器
     */
    private static final ThreadLocal<HttpServletRequest> REQUEST_CONTEXT = new InheritableThreadLocal<>();

    /**
     * 用户Jwt信息容器
     */
    private static final ThreadLocal<JwtBean> JWT_CONTEXT = new InheritableThreadLocal<>();

    private RequestContextUtils() {
    }

    /**
     * 获取当前HttpServletRequest
     * 1.先从当前容器中获取
     * 2.如果为空再从Spring容器中获取
     *
     * @return {@link HttpServletRequest}
     */
    public static HttpServletRequest getCurrentRequest() {
        HttpServletRequest request = REQUEST_CONTEXT.get();
        if (!Objects.isNull(request)) {
            return request;
        }

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(requestAttributes)) {
            return null;
        }

        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }


    /**
     * 将HttpServletRequest放入当前容器中
     *
     * @param request HttpServletRequest
     */
    public static void setRequest(HttpServletRequest request) {
        REQUEST_CONTEXT.set(request);
    }

    /**
     * 将用户的信息放入当前容器中
     *
     * @param jwt JwtBean
     */
    public static void setJwtBean(JwtBean jwt) {
        JWT_CONTEXT.set(jwt);
    }

    /**
     * 获取用户的JwtBean信息
     *
     * @return JwtBean
     */
    public static JwtBean getJwtBean() {
        return JWT_CONTEXT.get();
    }

    /**
     * 获取用户的用户号，如果没有登陆将会抛异常
     *
     * @return 用户号
     */
    public static Long getUserNo() {
        JwtBean jwt = getJwtBean();
        if (Objects.isNull(jwt)) {
            throw new BizException(BizCodeEnum.TOKEN_NOT_EXISTED_ERROR);
        }
        return jwt.getUserNo();
    }

    /**
     * 尝试获取用户的用户号信息，没有登陆不会异常
     *
     * @return 用户号
     */
    public static Long tryGetUserNo() {
        return getJwtBean() == null ? null : getJwtBean().getUserNo();
    }

    /**
     * 将容器清空，避免对内存造成浪费
     */
    public static void reset() {
        REQUEST_CONTEXT.remove();
        JWT_CONTEXT.remove();
    }

}
