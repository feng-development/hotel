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
package com.feng.hotel.base;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

/**
 * 常量类
 *
 * @author Administrator
 * @since 2021/7/14 17:14
 */
public interface Constants {

    /**
     * utf-8
     */
    String UTF_8 = "utf-8";

    /**
     * RSA
     */
    String RSA = "RSA";

    /**
     * utf-8字符集
     */
    Charset UTF_8_CHARSET = StandardCharsets.UTF_8;

    /**
     * http schema
     */
    String SCHEMA_HTTP = "http://";

    /**
     * https schema
     */
    String SCHEMA_HTTPS = "https://";

    /**
     * application json
     */
    String APPLICATION_JSON_CHARSET = "application/json;charset=utf-8";

    /**
     * application text html
     */
    String APPLICATION_TEXT_HTML_CHARSET = "text/html;charset=utf-8";

    /**
     * content type
     */
    String CONTENT_TYPE = "Content-Type";

    /**
     * 手机号码正则匹配
     */
    String REGEX_MOBILE_EXACT = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";

    /**
     * 身份证号码正则表达式
     */
    String REGEX_ID_CARD_EXACT = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";

    /**
     * 匹配手机号码Pattern
     */
    Pattern MOBILE_PATTERN = Pattern.compile(REGEX_MOBILE_EXACT);

    /**
     * 分隔符
     */
    String SPLIT = "/";

    /**
     * file name
     */
    String FILE_NAME = "filename";

    /**
     * 方法分隔符
     */
    String METHOD_SPLIT = "#";

    /**
     * 记录id
     */
    String TRACE_ID = "traceId";

    /**
     * 逗号
     */
    String COMMA = ",";

    /**
     * 中文逗号
     */
    String COMMA_CH = "，";

    /**
     * 下划线
     */
    String UNDERLINE = "_";

    /**
     * 中划线
     */
    String HYPHEN = "-";

    /**
     * 冒号
     */
    String COLON = ":";

    /**
     * 句号
     */
    String DOT = ".";

    /**
     * 邮箱发送协议
     */
    String PROTOCOL_SMTP = "smtp";

    /**
     * 开启Http的Debug
     */
    String ENABLE_DEBUG_HEADER = "enable_debug";

    /**
     * 默认树根节点id
     */
    Long DEFAULT_ROOT_ID = 0L;

}
