package com.feng.hotel.manager;

import com.feng.hotel.request.AuthRequest;

/**
 * @author Administrator
 * @since 2021/8/6
 */
public interface IUserManager {

    /**
     * 登录认证
     *
     * @param authRequest 登录信息
     */
    String auth(AuthRequest authRequest);
}
