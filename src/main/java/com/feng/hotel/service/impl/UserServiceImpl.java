package com.feng.hotel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.feng.hotel.domain.User;
import com.feng.hotel.mapper.UserMapper;
import com.feng.hotel.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.hotel.utils.Limit;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户  服务实现类
 * </p>
 *
 * @author evision
 * @since 2021-08-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public User getByLoginName(String loginName) {
        return this.getOne(
            Wrappers.<User>lambdaQuery()
                .eq(User::getLoginName, loginName)
                .last(Limit.lastOne())
        );
    }
}
