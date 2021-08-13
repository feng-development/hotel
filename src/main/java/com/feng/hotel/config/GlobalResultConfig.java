package com.feng.hotel.config;

import com.feng.hotel.base.entity.response.Result;
import com.feng.hotel.utils.json.JsonUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author Administrator
 * @since 2021/8/13
 */
@RestControllerAdvice
public class GlobalResultConfig implements ResponseBodyAdvice<Object> {

  @Override
  public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
      ServerHttpResponse response) {

    if (body instanceof Result) {
      return body;
    }
    //返回对象为string springmvc会使用StringHttpMessageConverter 会报错
    //对string做特殊化处理 提前转换为string
    if (body instanceof String) {
      return JsonUtils.serialize(Result.success(body));
    }
    return Result.success(body);
  }
}
