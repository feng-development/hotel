package com.feng.hotel.config;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Administrator
 * @since 2021/8/6
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer{

  @Value(value = "${exclude.path}")
  private List<String> excludePath;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    //注册TestInterceptor拦截器
    InterceptorRegistration registration = registry.addInterceptor(new TokenInterceptor());
    registration.addPathPatterns("/**");                      //所有路径都被拦截
    registration.excludePathPatterns("/**/login");
  }
}
