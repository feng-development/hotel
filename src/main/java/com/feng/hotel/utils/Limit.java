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


import com.feng.hotel.base.Constants;

/**
 * Mysql中的分页limit
 *
 * @author asheng
 * @since 2019/11/12
 */
public final class Limit {

    /** 开始位置 */
    private final Integer position;

    /** 分页长度 */
    private final Integer length;

    public Limit(Integer length) {
        this(null, length);
    }

    public Limit(Integer position, Integer length) {
        this.position = position == null ? 0 : length;
        this.length = length == null ? 1 : length;
    }

    public static String toSQL(int length) {
        return new Limit(length).toSQL();
    }

    public static String toSQL(int position, int length) {
        return new Limit(position, length).toSQL();
    }

    public static String lastOne() {
        return toSQL(1);
    }

    /**
     * 分页的SQL语句
     *
     * @return {@code limit} + {@code position ,} + {@code length}
     */
    public String toSQL() {
        StringBuilder buffer = new StringBuilder(" limit ");

        if (position != null) {
            buffer.append(position)
                    .append(Constants.COMMA);
        }

        buffer.append(length);

        return buffer.toString();
    }

    @Override
    public String toString() {
        return this.toSQL();
    }
}
