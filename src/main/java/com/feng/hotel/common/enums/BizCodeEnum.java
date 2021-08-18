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
package com.feng.hotel.common.enums;

import com.feng.hotel.base.exception.BizInfo;
import org.springframework.lang.NonNull;

/**
 * 业务枚举类，用于返回到客户端，给用户友好提示
 * {@code 0}表示系统正常, 其他的RC码均为异常
 * <p>
 * 1.{@code 0- 100}为系统级别变量，其中{@code 0-4}已经在BizInfo中被定义为常量
 * {@link BizCodeEnum#SUCCESS}                      成功
 * {@link BizCodeEnum#SYS_ERROR}                    系统异常
 * {@link BizCodeEnum#PARAM_VALIDATE_ERROR}         参数校验异常
 * {@link BizCodeEnum#FILE_UPLOAD_ERROR}            文件上传异常
 * {@link BizCodeEnum#REQUEST_TIMEOUT_ERROR}        请求超时异常
 * {@link BizCodeEnum#NO_AVAILABLE_SERVICE_ERROR}   请求的服务不可用
 * <p>
 * ... 其他系统级别的异常定义有待扩充
 * <p>
 * 2. 1000以下的系统是为了兼容之前金智云鼎的系统[以后尽量不扩充]
 * 目前扩充的是在签名和登录上，以后尽量不扩充，避免混乱
 * <p>
 * 3. 1000开始，为新的系统使用的RC码，每个系统各占一段，在使用新的RC码时，请注意避免冲突!!!
 * A.权限系统{@code 1000-1100}
 *
 * @author asheng
 * @since 2019/10/11
 */
public enum BizCodeEnum implements BizInfo {

    //============== 0-100 为系统级别的变量，0-4已经被占用 ===============
    REQUEST_TIMEOUT_ERROR("4", "访问超时，请稍后重试"),
    NO_AVAILABLE_SERVICE_ERROR("5", "当前访问的服务不可用，请稍后重试"),


    //============== 1000以下[包含1000]为了兼容之前系统 ===============
    SIGNATURE_ERROR("300", "接口签名错误"),
    TOKEN_EXPIRED_ERROR("600", "您的登录信息已失效，请重新登录"),
    TOKEN_NOT_EXISTED_ERROR("601", "用户token不存在"),
    TOKEN_ILLEGAL_ERROR("602", "用户token非法"),

    //============== 新系统的code从1001开始定义 ================

    //============== 权限系统，rc码分配从1001 - 1100  =============
    MENU_WITHOUT_PATH_ERROR("1001", "路由不能为空"),
    MENU_WITHOUT_COMPONENT_ERROR("1002", "组件不能为空"),
    MENU_META_INFO_PATTERN_ERROR("1003", "菜单元信息格式错误"),
    NO_SUCH_ROLE_ERROR("1004", "不存在此角色"),
    INSUFFICIENT_PERMISSION_ERROR("1005", "无权访问此资源，如需访问请联系管理员"),


    //============== 日志收集系统，rc码分配从1101 - 1200  =============
    LOGGER_NO_SUCH_PROJECT_ERROR("1101", "不存在此日志工程配置"),
    LOGGER_SAVE_FAILURE_ERROR("1102", "日志记录失败"),
    LOGGER_SAVE_TIMEOUT_ERROR("1103", "日志记录超时"),


    //============== 事件处理器系统，rc码分配从1201 - 1300  =============
    NO_EVENT_PROCESSOR_ERROR("1201", "不存在事件处理器"),
    NO_SUCH_EVENT_PROCESSOR_ERROR("1202", "不存在相应的事件处理器"),
    NO_SUCH_EVENT_ERROR("1203", "不存在此事件"),
    EVENT_PROCESS_OCCUR_ERROR("1204", "事件处理发生异常"),
    EVENT_CONTENT_FORMAT_ERROR("1205", "事件内容格式错误"),


    //============== 基础服务系统，rc码分配从1301 - 1400  =============
    ACQUIRE_AUTH_TOKEN_ERROR("1301", "获取实名认证Token异常"),
    ACQUIRE_AUTH_RESULT_ERROR("1302", "获取实名认证结果异常"),
    BAD_SMS_REQUEST_ERROR("1303", "发送短信请求不能为空"),
    EMPTY_SMS_RECIPIENT_ERROR("1304", "短信接收人不能为空"),
    EMPTY_SMS_SIGN_NAME_ERROR("1305", "短信签名不能为空"),
    EMPTY_SMS_TEMPLATE_ERROR("1305", "短信模版不能为空"),
    SMS_SEND_FAILURE_ERROR("1306", "短信发送失败"),
    NO_AVAILABLE_SMS_CHANNEL("1307", "暂无可用短信渠道"),
    MSG_SEND_FREQUENTLY_ERROR("1308", "发送过于频繁，请稍后重试"),
    VERIFY_CODE_NOT_MATCH_ERROR("1309", "短信校验码有误，请重试"),
    REACH_VERIFY_CODE_LIMIT_ERROR("1310", "获取短信验证码达到上限"),
    PHONE_PATTERN_ERROR("1311", "您输入的手机号有误，请重新输入"),
    PUSH_NOTIFICATION_ERROR("1312", "推送通知异常"),
    PUSH_TEMPLATE_NOT_EXIST_ERROR("1313", "推送模版不存在"),
    ;

    private final String code;

    private final String msg;

    BizCodeEnum(String code, @NonNull String msg) {
        this.code = code;
        this.msg = msg;
    }

    BizCodeEnum(@NonNull BizInfo bizInfo) {
        this.code = bizInfo.code();
        this.msg = bizInfo.msg();
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    @NonNull
    public String msg() {
        return msg;
    }

}
