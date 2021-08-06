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
package com.feng.hotel.base.exception;

import java.util.Date;
import java.util.Objects;


/**
 * 日期格式转换错误异常 为了更加清楚的标示日期转换的问题
 *
 * @author Administrator
 * @since 2021/7/14 17:14
 */
public class DateConverterException extends RuntimeException {

    public DateConverterException(String inputDate) {
        this(inputDate, null);
    }

    public DateConverterException(String inputDate, Throwable cause) {
        this(inputDate, null, cause);
    }

    public DateConverterException(String inputDate, String pattern, Throwable cause) {
        super("parse java.lang.String to java.util.Date error. input '" + inputDate
            + (Objects.isNull(pattern) ? "" : "'. pattern '" + pattern) + "'", cause);
    }

    public DateConverterException(Date date) {
        this(date, null);
    }

    public DateConverterException(Date date, Throwable cause) {
        super("parse java.util.Date to java.lang.String error. date '" + date + "'", cause);
    }

}
