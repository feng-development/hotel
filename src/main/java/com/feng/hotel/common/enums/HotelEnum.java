package com.feng.hotel.common.enums;

import com.feng.hotel.base.exception.BizInfo;
import lombok.Getter;

/**
 * @author Administrator
 * @since 2021/8/3
 */

public enum HotelEnum implements BizInfo {
  AUTH_ERROR("123", "账号或密码错误");
  private final String code;

  private final String msg;

  HotelEnum(String code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  @Override
  public String code() {
    return this.code;
  }

  @Override
  public String msg() {
    return this.msg;
  }
}
