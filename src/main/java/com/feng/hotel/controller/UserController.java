package com.feng.hotel.controller;


import com.feng.hotel.base.entity.response.Result;
import com.feng.hotel.manager.IUserManager;
import com.feng.hotel.request.AuthRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户  前端控制器
 * </p>
 *
 * @author evision
 * @since 2021-08-05
 */
@RestController
@RequestMapping("/user")
public class UserController {

  private final IUserManager userManager;

  public UserController(IUserManager userManager) {
    this.userManager = userManager;
  }

  @PostMapping(value = "login")
  public Result<?> auth(@RequestBody AuthRequest authRequest) {
    return Result.success(userManager.auth(authRequest));
  }
}

