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

import com.alibaba.fastjson.JSONObject;
import com.feng.hotel.base.exception.BizException;
import com.feng.hotel.base.exception.BizInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Collection;


/**
 * 返回结果对象
 *
 * @author Administrator
 * @since 2021/7/14 17:14
 */
@ApiModel(value = "返回结果")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 6165485211756929746L;

    @ApiModelProperty(value = "业务编号, 仅0为成功")
    private String status;

    @ApiModelProperty(value = "业务信息")
    private String msg;

    @ApiModelProperty(value = "业务数据对象")
    private T data;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    /**
     * 返回错误结果
     *
     * @param e 业务异常
     */
    public static <T> Result<T> error(BizException e) {
        return error(e.getCode(), e.getMsg());
    }

    /**
     * 返回错误结果
     *
     * @param e    业务异常
     * @param data 业务结果
     */
    public static <T> Result<T> error(BizException e, T data) {
        return error(e.getCode(), e.getMsg(), data);
    }

    /**
     * 返回错误结果
     *
     * @param info 业务信息
     */
    public static <T> Result<T> error(BizInfo info) {
        return error(info.code(), info.msg());
    }

    /**
     * 返回错误结果
     *
     * @param info 业务信息
     * @param data 数据
     * @param <T>  数据类型
     * @return 业务结果
     */
    public static <T> Result<T> error(BizInfo info, T data) {
        return error(info.code(), info.msg(), data);
    }

    /**
     * 返回错误结果
     *
     * @param code 返回代码
     * @param msg  返回信息
     */
    public static <T> Result<T> error(String code, String msg) {
        return error(code, msg, null);
    }

    /**
     * 返回错误结果
     *
     * @param status 返回代码
     * @param msg    返回信息
     * @param t      错误提示
     */
    public static <T> Result<T> error(String status, String msg, T t) {
        Result<T> res = new Result<>();
        res.setStatus(status);
        res.setMsg(msg);
        res.setData(t);
        return res;
    }

    /**
     * 返回成功的结果，不返回给前端结果集，只有retCode
     * 表示此次操作成功
     */
    public static <T> Result<T> success() {
        Result<T> res = new Result<>();
        res.setStatus(BizInfo.SUCCESS.code());
        res.setMsg(BizInfo.SUCCESS.msg());
        return res;
    }

    /**
     * 返回成功的结果
     *
     * @param t 返回结果
     */
    public static <T> Result<T> success(T t) {
        Result<T> res = new Result<>();
        res.setStatus(BizInfo.SUCCESS.code());
        res.setMsg(BizInfo.SUCCESS.msg());
        res.setData(t);
        return res;
    }

    /**
     * 返回成功的列表结果
     *
     * @param t 结果列表
     */
    public static <T> Result<Pagination<T>> success(Collection<T> t) {
        if (t == null || t.isEmpty()) {
            return success(0, null);
        }
        return success(t.size(), t);
    }

    /**
     * 返回成功的分页对象
     *
     * @param total 总条数
     * @param t     结果列表
     */
    public static <T> Result<Pagination<T>> success(int total, Collection<T> t) {
        Pagination<T> pagination = Pagination.page(total, t);
        return success(pagination);
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
