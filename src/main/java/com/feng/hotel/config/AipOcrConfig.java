package com.feng.hotel.config;

import com.baidu.aip.ocr.AipOcr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 * @since 2021/8/9
 */
@Configuration
public class AipOcrConfig {

  @Value(value = "${baidu.appId}")
  private String APP_ID;
  @Value(value = "${baidu.apiKey}")
  private String API_KEY;
  @Value(value = "${baidu.secretKey}")
  private String SECRET_KEY;

  @Bean
  public AipOcr getAipOcr() {
   return  new AipOcr(APP_ID, API_KEY, SECRET_KEY);
  }
}
