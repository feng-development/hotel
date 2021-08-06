/*
 *  Copyright 1999-2019 Evision Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.feng.hotel.config.mybatis;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

/**
 * 增强后的mybatis mapper
 *
 * @author asheng
 * @since 2020/8/17
 */
public interface MybatisBaseMapper<T> extends BaseMapper<T> {

    /**
     * 判断是否存在该记录
     *
     * @param wrapper 查询构造器
     * @return {@code true: 存在} {@code false: 不存在}
     */
    boolean exist(@Param(Constants.WRAPPER) Wrapper<T> wrapper);

}
