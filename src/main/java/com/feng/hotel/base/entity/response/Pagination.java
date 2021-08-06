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
package com.feng.hotel.base.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 分页对象
 *
 * @author Administrator
 * @since 2021/7/14 17:14
 */
@ApiModel(value = "分页对象")
public class Pagination<T> {

    @SuppressWarnings("rawtypes")
    private final static Pagination EMPTY = Pagination.page(0, Collections.emptyList());

    @ApiModelProperty(value = "分页总条数")
    private int total;

    @ApiModelProperty(value = "分页列表", required = true)
    private List<T> result;

    /**
     * 生成分页对象
     *
     * @param total 分页个数
     * @param list  当前分页列表
     * @param <T>   对象类型
     * @return 分页对象
     */
    public static <T> Pagination<T> page(int total, Collection<T> list) {
        Pagination<T> pagination = new Pagination<>();
        pagination.total = total;
        pagination.result = list == null ? Collections.emptyList() : new ArrayList<>(list);
        return pagination;
    }

    /**
     * 生成分页对象（适用于分页个数为列表个数的场景）
     *
     * @param list 当前分页列表
     * @param <T>  对象类型
     * @return 分页对象
     */
    public static <T> Pagination<T> page(Collection<T> list) {
        if (list == null) {
            return empty();
        }

        Pagination<T> pagination = new Pagination<>();
        pagination.total = list.size();
        pagination.result = new ArrayList<>(list);
        return pagination;
    }

    /**
     * 生成空的分页对象
     *
     * @param <T> 对象类型
     * @return 空的分页对象
     */
    @SuppressWarnings("unchecked")
    public static <T> Pagination<T> empty() {
        return (Pagination<T>) EMPTY;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public boolean isEmpty() {
        return total == 0;
    }

    /**
     * 将结果对象转成set
     *
     * @return set结构的结果对象
     */
    public Set<T> toSet() {
        return new HashSet<>(this.getResult());
    }
}
