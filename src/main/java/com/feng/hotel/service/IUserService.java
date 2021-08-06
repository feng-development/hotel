package com.feng.hotel.service;

import com.feng.hotel.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户  服务类
 * </p>
 *
 * @author evision
 * @since 2021-08-05
 */
public interface IUserService extends IService<User> {

  /**
   * 根据用户名互殴去
   *
   * @param loginName 登录名
   * @return 用户信息
   */
  User getByLoginName(String loginName);
}
