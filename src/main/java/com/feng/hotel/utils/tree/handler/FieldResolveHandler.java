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
package com.feng.hotel.utils.tree.handler;


import com.feng.hotel.annotation.IdField;
import com.feng.hotel.annotation.OrderField;
import com.feng.hotel.annotation.ParentIdField;
import com.feng.hotel.base.Constants;
import com.feng.hotel.base.OrderType;
import com.feng.hotel.utils.GlobalUtils;
import com.feng.hotel.utils.JdkUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 字段处理器
 * 1.查看字段是否有{@link IdField}注解以及{@link ParentIdField}
 * 2.查询此字段是否需要排序，以及排序规则
 *
 * @author asheng
 * @since 2019/10/14
 */
public final class FieldResolveHandler {

    /** 对字段处理器的缓存 */
    private static final Map<Class<?>, FieldResolveHandler> HANDLER_CACHE = new ConcurrentHashMap<>();

    /** 对Id字段进行缓存 */
    private static final Map<Class<?>, Field> ID_FIELD_CACHE = new ConcurrentHashMap<>();

    /** 对ParentId进行缓存 */
    private static final Map<Class<?>, Field> PARENT_ID_FIELD_CACHE = new ConcurrentHashMap<>();

    /** 对排序字段进行缓存 */
    private static final Map<Class<?>, Field> ORDER_FIELD_CACHE = new ConcurrentHashMap<>();

    private final Class<?> clazz;

    private FieldResolveHandler(Class<?> clazz) {
        this.clazz = clazz;

        resolveFields();

        HANDLER_CACHE.put(clazz, this);
    }

    public static FieldResolveHandler getInstance(Class<?> clazz) {
        return HANDLER_CACHE.get(clazz) == null ? new FieldResolveHandler(clazz) : HANDLER_CACHE.get(clazz);
    }

    /**
     * 解析目前类的所有字段
     *
     * 1.对类的三种需要分析的字段进行缓存
     * 2.Id字段和ParentId字段必须只能有一个
     * 3.Order字段有且只能有一个
     */
    private void resolveFields() {
        Field idField = ID_FIELD_CACHE.get(clazz);
        if (idField != null) {
            return;
        }

        Field[] fields = clazz.getDeclaredFields();
        if (GlobalUtils.isNull(fields)) {
            throw new RuntimeException("there is no declared fields in class '" + clazz.getName() + "'");
        }

        int idFieldCount = 0;

        int parentFieldCount = 0;

        int orderFieldCount = 0;

        for (Field field : fields) {
            boolean presentId = field.isAnnotationPresent(IdField.class);
            if (presentId) {
                isAssignFrom(field, Integer.class, int.class, Long.class, long.class, String.class);
                ID_FIELD_CACHE.put(clazz, field);
                idFieldCount ++;
            }

            boolean presentParentId = field.isAnnotationPresent(ParentIdField.class);
            if (presentParentId) {
                isAssignFrom(field, Integer.class, int.class, Long.class, long.class, String.class);
                PARENT_ID_FIELD_CACHE.put(clazz, field);
                parentFieldCount ++;
            }

            boolean presentOrder = field.isAnnotationPresent(OrderField.class);
            if (presentOrder) {
                Class<?> fieldType = field.getType();
                if (!Comparable.class.isAssignableFrom(fieldType)) {
                    throw new RuntimeException("field with @OrderField must assign from '" + Comparable.class.getName() + "'");
                }

                ORDER_FIELD_CACHE.put(clazz, field);
                orderFieldCount ++;
            }
        }

        if (idFieldCount != 1) {
            reset();
            throw new RuntimeException("there must be one field annotation with @IdFiled in class '" + clazz.getName() + "'");
        }

        if (parentFieldCount != 1) {
            reset();
            throw new RuntimeException("there must be one field annotation with @ParentIdField in class '" + clazz.getName() + "'");
        }

        if (orderFieldCount > 1) {
            reset();
            throw new RuntimeException("there must less then one field annotation with @OrderField in class '" + clazz.getName() + "'");
        }
    }

    /**
     * 判断字段的类型是否来自于以下的类型数据
     *
     * @param field     字段
     * @param classes   类型数组
     */
    private void isAssignFrom(Field field, Class<?> ... classes) {
        Class<?> fieldType = field.getType();
        boolean isAssignFrom = false;

        StringJoiner joiner = new StringJoiner(Constants.COMMA);

        for (Class<?> clazz : classes) {
            if (clazz.isAssignableFrom(fieldType)) {
                isAssignFrom = true;
            }
            joiner.add(clazz.getName());
        }

        if (isAssignFrom) {
            return;
        }

        reset();
        throw new RuntimeException("now field annotation with @IdField and @ParentIdField only support followed class: [" + joiner.toString() + "]");
    }

    /**
     * 将当前类的缓存重置
     *
     * 1. 重置IdField缓存
     * 2. 重置ParentIdField缓存
     * 3. 重置OrderField缓存
     */
    private void reset() {
        ID_FIELD_CACHE.remove(clazz);
        PARENT_ID_FIELD_CACHE.remove(clazz);
        ORDER_FIELD_CACHE.remove(clazz);
    }

    public Object getId(Object object) {
        return JdkUtils.invokeGetter(object, ID_FIELD_CACHE.get(clazz));
    }

    Object getParentId(Object object) {
        return JdkUtils.invokeGetter(object, PARENT_ID_FIELD_CACHE.get(clazz));
    }

    Comparable<Object> getOrder(Object object) {
        Field orderField = ORDER_FIELD_CACHE.get(clazz);
        if (orderField == null) {
            throw new RuntimeException("can not find field  annotation with @OrderField");
        }

        //noinspection unchecked
        return (Comparable<Object>) JdkUtils.invokeGetter(object, ORDER_FIELD_CACHE.get(clazz));
    }

    boolean presentOrder() {
        return ORDER_FIELD_CACHE.get(clazz) != null;
    }

    OrderType getOrderType() {
        return ORDER_FIELD_CACHE.get(clazz).getAnnotation(OrderField.class).type();
    }
}
