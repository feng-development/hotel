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
package com.feng.hotel.utils.date;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Thread safe date util 线程安全的日期工具类
 *
 * @author asheng
 * @since 2018/8/9
 */
public final class DateUtils {

    private static final ThreadLocal<SimpleDateFormat> LOCAL_DATE_FORMAT = new ThreadLocal<>();

    private DateUtils() {
    }

    /**
     * 获取ThreadLocal中的SimpleDateFormatter实例
     *
     * @return {@link SimpleDateFormat}
     */
    private static SimpleDateFormat getFormatter() {
        SimpleDateFormat formatter = LOCAL_DATE_FORMAT.get();
        if (formatter == null) {
            formatter = new SimpleDateFormat();
            LOCAL_DATE_FORMAT.set(formatter);
        }
        return formatter;
    }

    /**
     * 对LocalDateTime进行format处理，建议使用本方法
     *
     * @param localDateTime 日期时间
     * @param pattern       格式
     * @return 格式化后对日志
     */
    public static String format(LocalDateTime localDateTime, String pattern) {
        if (localDateTime == null) {
            throw new NullPointerException("Input LocalDateTime is null.");
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * 把{@link LocalDateTime} 转换成为 {@link Date} 类型
     *
     * @param localDateTime LocalDateTime
     * @return Date类型日期
     */
    public static Date toDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            throw new NullPointerException("Input LocalDateTime is null.");
        }

        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /**
     * 把{@link Date} 转换为 {@link LocalDateTime} 类型
     *
     * @param date Date
     * @return LocalDateTime类型
     */
    public static LocalDateTime toLocalDate(Date date) {
        if (date == null) {
            throw new NullPointerException("Input Date is null.");
        }

        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 获取某天的开始时间，即获取该输入天的开始时间
     *
     * <pre>
     *     eg: input: 2019-02-23 10:01:23 -> output: 2019-02-23 00:00:00
     * </pre>
     * <p>
     * Note:如果需要输出为LocalDateTime可以调用{@link DateUtils#toLocalDate(Date)}
     *
     * @param date 输入的日期
     * @return 该输入日期当天的开始时间
     */
    public static Date atStartOfDay(Date date) {
        return atStartOfDay(toLocalDate(date));
    }

    /**
     * 获取某天的开始时间，即获取该输入天的开始时间
     *
     * <pre>
     *     eg: input: 2019-02-23 10:01:23 -> output: 2019-02-23 00:00:00
     * </pre>
     * <p>
     * Note:如果需要输出为LocalDateTime可以调用{@link DateUtils#toLocalDate(Date)}
     *
     * @param dateTime 输入的日期
     * @return 该输入日期当天的开始时间
     */
    public static Date atStartOfDay(LocalDateTime dateTime) {

        LocalDate localDate = dateTime.toLocalDate();

        LocalDateTime startLocalDateTime = localDate.atStartOfDay();

        return toDate(startLocalDateTime);
    }


    /**
     * 获取某月的开始时间，即获取该输入天所在的月开始时间
     *
     * <pre>
     *     eg: input: 2019-02-23 10:01:23 -> output: 2019-02-01 00:00:00
     * </pre>
     * <p>
     * Note:如果需要输出为LocalDateTime可以调用{@link DateUtils#toLocalDate(Date)}
     *
     * @param dateTime 输入的日期
     * @return 该输入日期当月的开始时间
     */
    public static Date atStartOfMonth(Date dateTime) {
        return atStartOfMonth(toLocalDate(dateTime));
    }

    /**
     * 获取某月的开始时间，即获取该输入天所在的月开始时间
     *
     * <pre>
     *     eg: input: 2019-02-23 10:01:23 -> output: 2019-02-01 00:00:00
     * </pre>
     * <p>
     * Note:如果需要输出为LocalDateTime可以调用{@link DateUtils#toLocalDate(Date)}
     *
     * @param dateTime 输入的日期
     * @return 该输入日期当月的开始时间
     */
    public static Date atStartOfMonth(LocalDateTime dateTime) {

        LocalDate localDate = dateTime.toLocalDate()
            .withDayOfMonth(1);

        LocalDateTime startLocalDateTime = localDate.atStartOfDay();

        return toDate(startLocalDateTime);
    }

    /**
     * 获取某年的开始时间，即获取该输入天所在的月开始时间
     *
     * <pre>
     *     eg: input: 2019-02-23 10:01:23 -> output: 2019-02-01 00:00:00
     * </pre>
     * <p>
     * Note:如果需要输出为LocalDateTime可以调用{@link DateUtils#toLocalDate(Date)}
     *
     * @param dateTime 输入的日期
     * @return 该输入日期当天的开始时间
     */
    public static Date atStartOfYear(Date dateTime) {

        return atStartOfYear(toLocalDate(dateTime));
    }

    /**
     * 获取某年的开始时间，即获取该输入天所在的月开始时间
     *
     * <pre>
     *     eg: input: 2019-02-23 10:01:23 -> output: 2019-02-01 00:00:00
     * </pre>
     * <p>
     * Note:如果需要输出为LocalDateTime可以调用{@link DateUtils#toLocalDate(Date)}
     *
     * @param dateTime 输入的日期
     * @return 该输入日期当天的开始时间
     */
    public static Date atStartOfYear(LocalDateTime dateTime) {

        LocalDate localDate = dateTime.toLocalDate()
            .withDayOfYear(1);

        LocalDateTime startLocalDateTime = localDate.atStartOfDay();

        return toDate(startLocalDateTime);
    }


    /**
     * 获取某月的结束时间，即获取该输入天的结束时间
     *
     * <pre>
     *     eg: input: 2019-02-23 10:01:23 -> output: 2019-02-31 23:59:59
     * </pre>
     * <p>
     * Note:如果需要输出为LocalDateTime可以调用{@link DateUtils#toLocalDate(Date)}
     *
     * @param dateTime 输入的日期
     * @return 该输入日期当月的开始时间
     */
    public static Date atEndOfMonth(Date dateTime) {

        return atEndOfMonth(toLocalDate(dateTime));

    }

    /**
     * 获取某月的结束时间，即获取该输入天的结束时间
     *
     * <pre>
     *     eg: input: 2019-02-23 10:01:23 -> output: 2019-02-31 23:59:59
     * </pre>
     * <p>
     * Note:如果需要输出为LocalDateTime可以调用{@link DateUtils#toLocalDate(Date)}
     *
     * @param dateTime 输入的日期
     * @return 该输入日期当月的开始时间
     */
    public static Date atEndOfMonth(LocalDateTime dateTime) {
        LocalDate localDate = dateTime.toLocalDate();

        LocalDate tomorrow = localDate.plusMonths(1).withDayOfMonth(1);

        LocalDateTime startOfTomorrow = tomorrow.atStartOfDay();

        long lastTimestamp = toTimestamp(startOfTomorrow) - 1;

        return new Date(lastTimestamp);
    }

    /**
     * 获取某年的结束时间，即获取该输入天所在的年的结束时间
     *
     * <pre>
     *     eg: input: 2019-02-23 10:01:23 -> output: 2019-12-31 23:59:59
     * </pre>
     * <p>
     * Note:如果需要输出为LocalDateTime可以调用{@link DateUtils#toLocalDate(Date)}
     *
     * @param dateTime 输入的日期
     * @return 该输入日期当月的开始时间
     */
    public static Date atEndOfYear(Date dateTime) {

        return atEndOfYear(toLocalDate(dateTime));

    }

    /**
     * 获取某年的结束时间，即获取该输入天所在的年的结束时间
     *
     * <pre>
     *     eg: input: 2019-02-23 10:01:23 -> output: 2019-12-31 23:59:59
     * </pre>
     * <p>
     * Note:如果需要输出为LocalDateTime可以调用{@link DateUtils#toLocalDate(Date)}
     *
     * @param dateTime 输入的日期
     * @return 该输入日期当月的开始时间
     */
    public static Date atEndOfYear(LocalDateTime dateTime) {
        LocalDate localDate = dateTime.toLocalDate();

        LocalDate tomorrow = localDate.plusYears(1).withDayOfYear(1);

        LocalDateTime startOfTomorrow = tomorrow.atStartOfDay();

        long lastTimestamp = toTimestamp(startOfTomorrow) - 1;

        return new Date(lastTimestamp);
    }

    /**
     * 获取某天的结束时间，即获取该输入天的结束时间
     *
     * <pre>
     *     eg: input: 2019-02-23 10:01:23 -> output: 2019-02-23 23:59:59
     * </pre>
     * <p>
     * Note:如果需要输出为LocalDateTime可以调用{@link DateUtils#toLocalDate(Date)}
     *
     * @param date 输入的日期
     * @return 该输入日期当天的开始时间
     */
    public static Date atEndOfDay(Date date) {
        return atEndOfDay(toLocalDate(date));
    }

    /**
     * 获取某天的结束时间，即获取该输入天的结束时间
     *
     * <pre>
     *     eg: input: 2019-02-23 10:01:23 -> output: 2019-02-23 23:59:59
     * </pre>
     * <p>
     * Note:如果需要输出为LocalDateTime可以调用{@link DateUtils#toLocalDate(Date)}
     *
     * @param dateTime 输入的日期
     * @return 该输入日期当天的开始时间
     */
    public static Date atEndOfDay(LocalDateTime dateTime) {
        LocalDate localDate = dateTime.toLocalDate();

        LocalDate tomorrow = localDate.plusDays(1);

        LocalDateTime startOfTomorrow = tomorrow.atStartOfDay();

        long lastTimestamp = toTimestamp(startOfTomorrow) - 1;

        return new Date(lastTimestamp);
    }

    /**
     * 把{@link LocalDateTime} 转换成为时间戳
     *
     * @param dateTime 输入日期
     * @return 时间戳
     */
    public static Long toTimestamp(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 将日期格式化输出
     *
     * @param date    日期
     * @param pattern 格式
     * @return 格式化后的日期
     */
    public static String format(Date date, String pattern) {
        if (Objects.isNull(date)) {
            return null;
        }

        try {
            SimpleDateFormat formatter = getFormatter();
            formatter.applyPattern(pattern);
            return formatter.format(date);
        } finally {
            LOCAL_DATE_FORMAT.remove();
        }
    }

    /**
     * 将时间戳格式化输出
     *
     * @param timestamp 时间戳
     * @param pattern   格式
     * @return 格式化后的日期
     */
    public static String format(Long timestamp, String pattern) {
        return format(new Date(timestamp), pattern);
    }

    /**
     * 将格式化的日期转成日期
     *
     * @param strDate 格式化的日期
     * @param pattern 日期格式
     * @return 日期
     */
    public static Date parse(String strDate, String pattern) {
        if (StringUtils.isEmpty(strDate)) {
            return null;
        }

        try {
            SimpleDateFormat formatter = getFormatter();
            formatter.applyPattern(pattern);
            try {
                return formatter.parse(strDate);
            } catch (ParseException e) {
                throw new NullPointerException("parse error");
            }
        } finally {
            LOCAL_DATE_FORMAT.remove();
        }
    }

    /**
     * 将当前时间格式化输出
     *
     * @param pattern 格式
     * @return 当前时间的格式输出
     */
    public static String now(String pattern) {
        return format(LocalDateTime.now(), pattern);
    }

    /**
     * 获取两个时间段中每天的日期[包头包尾]
     *
     * @param startDay 开始时间
     * @param endDay   结束时间
     * @return 格式化后的日期
     */
    public static List<String> betweenDays(Date startDay, Date endDay) {
        if (startDay.compareTo(endDay) >= 0) {
            return Collections.emptyList();
        }

        Calendar start = Calendar.getInstance();
        start.setTime(startDay);

        Calendar end = Calendar.getInstance();
        end.setTime(endDay);

        ArrayList<String> days = new ArrayList<>();
        do {
            days.add(DatePattern.DATE.format(start.getTime()));
            start.add(Calendar.DATE, 1);
        } while (start.compareTo(end) < 0);
        return days;
    }

    /**
     * 获取当天的开始时间
     *
     * @return 时间
     */
    public static Date getTodayStartTime() {
        return atStartOfDay(LocalDateTime.now());
    }

    /**
     * 获取当天的结束时间
     *
     * @return 时间
     */
    public static Date getTodayEndTime() {
        return atEndOfDay(LocalDateTime.now());
    }

    /**
     * 日期相加减
     *
     * @param date   基础日期
     * @param years  加减年份数
     * @param months 加减月份数
     * @param days   加减天数
     * @return 结果日期
     */
    public static Date addDate(Date date, int years, int months, int days) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.YEAR, years);
        now.add(Calendar.MONTH, months);
        now.add(Calendar.DAY_OF_MONTH, days);

        return now.getTime();
    }

    /**
     * 获取两个时间相差的天数
     *
     * @return 相差的天数
     */
    public static int getDiffDays(Date startDay, Date endDay) {
        //获取第一个时间点的时间戳对应的秒数
        long t1 = toLocalDateTime(startDay).toEpochSecond(ZoneOffset.ofHours(0));
        //获取第一个时间点在是1970年1月1日后的第几天
        long day1 = t1 / (60 * 60 * 24);
        //获取第二个时间点的时间戳对应的秒数
        long t2 = toLocalDateTime(endDay).toEpochSecond(ZoneOffset.ofHours(0));
        //获取第二个时间点在是1970年1月1日后的第几天
        long day2 = t2 / (60 * 60 * 24);
        //返回两个时间点的天数差
        return (int) (day2 - day1);
    }


    /**
     * 获取两个时间相差的小时
     *
     * @return 相差的天数
     */
    public static int getDiffHour(Date startDay, Date endDay) {

        Duration between = Duration.between(toLocalDate(startDay), toLocalDate(endDay));
        return (int) between.toHours();
    }

    /**
     * 获取两个时间相差的分钟
     *
     * @return 相差的天数
     */
    public static int getDiffMinutes(Date startDay, Date endDay) {
        Duration between = Duration.between(toLocalDate(startDay), toLocalDate(endDay));
        return (int) between.toMinutes();
    }

    /**
     * dateTime  在startTime和endTime区间内
     *
     * @param dateTime  时间
     * @param startTime 范围开始时间
     * @param endTime   范围结束时间
     * @return 是否在区间内 true 在区间内
     */
    public static boolean isWithin(LocalDateTime dateTime, LocalDateTime startTime,
                                   LocalDateTime endTime) {
        return startTime.compareTo(dateTime) <= 0 && endTime.compareTo(dateTime) >= 0;
    }

    /**
     * dateTime  在startTime和endTime区间内
     *
     * @param dateTime  时间
     * @param startTime 范围开始时间
     * @param endTime   范围结束时间
     * @return 是否在区间内 true 在区间内
     */
    public static boolean isWithin(Date dateTime, Date startTime, Date endTime) {
        return startTime.compareTo(dateTime) <= 0 && endTime.compareTo(dateTime) >= 0;
    }

    /**
     * 把{@link Date} 转换为 {@link LocalDateTime} 类型
     *
     * @param date Date
     * @return LocalDateTime类型
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            throw new NullPointerException("Input Date is null.");
        }

        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 获取时间的小时数
     *
     * @param date 时间
     * @return 小时 0-24
     */
    public static int getHour(Date date) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        return localDateTime.getHour();
    }

}
