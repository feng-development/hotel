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
package com.feng.hotel.base.entity.request;


import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;

/**
 * 分页请求对象
 *
 * @author Administrator
 * @since 2021/7/14 17:14
 */
public class PageRequest extends BaseRequest {

    @ApiModelProperty(value = "起始页")
    private int page = 1;

    @ApiModelProperty(value = "分页大小")
    private int size = 5;

    /**
     * 起始地址，算法 (page - 1) * offset
     *
     * @return 查询起始位置
     */
    public int getOffset() {
        return (page - 1) * size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        if (page < 1) {
            throw new IllegalArgumentException("page can not be less than 1");
        }

        this.page = page;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
