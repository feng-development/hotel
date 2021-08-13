package com.feng.hotel.controller;

import com.feng.hotel.utils.RequestContextUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @since 2021/8/13
 */
@RestController()
@RequestMapping(value = "/file")
public class FileUploadController {


  @PostMapping(value = "/upload")
  public String upload() {
   return RequestContextUtils.getCurrentRequest().getContextPath();
  }
}
