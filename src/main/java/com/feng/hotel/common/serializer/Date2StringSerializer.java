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
package com.feng.hotel.common.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

/**
 * 处理日期类型为long类型，然后再转为string类型，避免丢失精度传递给前端
 *
 * @author asheng
 * @since 2019/9/29
 */
public class Date2StringSerializer implements ObjectSerializer {

    public static Date2StringSerializer instance = new Date2StringSerializer();

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName,
                      Type fieldType, int features) throws IOException {
        if (object == null) {
            serializer.writeNull();
            return;
        }

        if (object instanceof Date) {
            Date date = (Date) object;
            serializer.getWriter().writeString(String.valueOf(date.getTime()));
        }
    }
}
