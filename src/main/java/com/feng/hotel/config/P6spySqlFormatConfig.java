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
package com.feng.hotel.config;

import com.feng.hotel.utils.date.DatePattern;
import com.feng.hotel.utils.date.DateUtils;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 使用P6spy对sql表现进行打印。性能上基本上没什么浪费
 * 后期可能会需要根据此选项对sql进行监控
 * 因此去除了MybatisPlus对PerformanceInterceptor插件
 *
 * @author asheng
 * @since 2019/10/15
 */
public class P6spySqlFormatConfig implements MessageFormattingStrategy {

    private static final Pattern PATTERN = Pattern.compile("[\\s]+");

    /**
     * Formats a log message for the logging module
     *
     * @param connectionId the id of the connection
     * @param now          the current ime expressing in milliseconds
     * @param elapsed      the time in milliseconds that the operation took to complete
     * @param category     the category of the operation
     * @param prepared     the SQL statement with all bind variables replaced with actual values
     * @param sql          the sql statement executed
     * @param url          the database url where the sql statement executed
     * @return the formatted log message
     */
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category,
                                String prepared, String sql, String url) {

        return StringUtils.isNotBlank(sql) ?
            DateUtils.format(LocalDateTime.now(), DatePattern.DATETIME.pattern())
                + " | cost: " + elapsed + " ms | sql ：" + PATTERN.matcher(sql)
                .replaceAll(StringUtils.SPACE) + ";" : StringUtils.EMPTY;
    }
}
