package com.feng.hotel.service;

import com.feng.hotel.response.IdCardResult;
import javax.swing.Spring;

/**
 * @author Administrator
 * @since 2021/8/9
 */
public interface IIdCardService {

  /**
   * 身份证识别
   *
   * @param path 身份证路径
   * @return 身份证信息
   */
  IdCardResult idCardRecognition(String path);
}
