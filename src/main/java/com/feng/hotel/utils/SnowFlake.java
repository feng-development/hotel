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
package com.feng.hotel.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.NetworkInterface;
import java.util.Enumeration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * id自增器（雪花算法）
 */
public final class SnowFlake {

    private static final Logger LOGGER = LoggerFactory.getLogger(SnowFlake.class);

    /**
     * 初始化基准时间戳
     */
    private final static long TWEPOCH = 12888349746579L;

    /**
     * 机器标识位数
     */
    private final static long WORKER_ID_BITS = 5L;

    /**
     * 数据中心标识位数
     */
    private final static long DATA_CENTER_ID_BITS = 5L;

    /**
     * 毫秒内自增位数
     */
    private final static long SEQUENCE_BITS = 12L;

    /**
     * 机器ID偏左移12位
     */
    private final static long WORKER_ID_SHIFT = SEQUENCE_BITS;

    /**
     * 数据中心ID左移17位
     */
    private final static long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    /**
     * 时间毫秒左移22位
     */
    private final static long TIMESTAMP_LEFT_SHIFT =
        SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;

    /**
     * sequence掩码，确保sequence不会超出上限
     */
    private final static long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    /**
     * 上次时间戳
     */
    private static long lastTimestamp = -1L;

    /**
     * 序列
     */
    private long sequence = 0L;

    /**
     * 服务器ID
     */
    private long workerId;

    /**
     * 进程编码
     */
    private long processId;

    private static final SnowFlake SNOW_FLAKE = new SnowFlake();

    private SnowFlake() {
        try {
            // 获取机器编码
            this.workerId = this.getMacAddress();
            // 获取进程编码
            RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
            this.processId = Long.parseLong(runtimeMXBean.getName().split("@")[0]);
            // 避免编码超出最大值
            long workerMask = ~(-1L << WORKER_ID_BITS);
            this.workerId = workerId & workerMask;
            long processMask = ~(-1L << DATA_CENTER_ID_BITS);
            this.processId = processId & processMask;
        } catch (Exception e) {
            throw new RuntimeException("init snow flake error.", e);
        }
    }

    /**
     * 获取id
     *
     * @return id
     */
    public static synchronized long nextId() {
        return SNOW_FLAKE.getNextId();
    }

    /**
     * 获取下一个Id
     *
     * @return id
     */
    private synchronized long getNextId() {
        // 获取时间戳
        long timestamp = timeGen();
        // 如果时间戳小于上次时间戳则报错
        if (timestamp < lastTimestamp) {
            LOGGER.error("时间戳小于上次时间戳，请确认服务器时间是否被调整过！");
        }
        // 如果时间戳与上次时间戳相同
        if (lastTimestamp == timestamp) {
            // 当前毫秒内，则+1，与sequenceMask确保sequence不会超出上限
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                // 当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        // ID偏移组合生成最终的ID，并返回ID
        return ((timestamp - TWEPOCH) << TIMESTAMP_LEFT_SHIFT) | (processId << DATA_CENTER_ID_SHIFT)
            | (workerId << WORKER_ID_SHIFT) | sequence;
    }

    /**
     * 再次获取时间戳直到获取的时间戳与现有的不同
     *
     * @param lastTimestamp 最后的时间戳
     * @return 下一个时间戳
     */
    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    /**
     * 当前时间戳
     *
     * @return 时间戳
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 获取mac地址，和原来农行的算法保持一直，因此使用了Enumeration进行迭代获取第一个
     *
     * @return mac地址的hashcode
     * @throws Exception Exception
     */
    private long getMacAddress() throws Exception {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            if (networkInterface == null) {
                continue;
            }

            byte[] mac = networkInterface.getHardwareAddress();
            if (mac == null) {
                continue;
            }

            return hexMac(mac).hashCode();
        }
        return 1L;
    }

    /**
     * 对mac地址进行十六进制处理
     *
     * @param mac mac地址
     * @return hex后的内容
     */
    private String hexMac(byte[] mac) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append(":");
            }
            //字节转换为整数
            int temp = mac[i] & 0xff;
            String str = Integer.toHexString(temp);
            if (str.length() == 1) {
                sb.append("0").append(str);
            } else {
                sb.append(str);
            }
        }
        return sb.toString();
    }

}
