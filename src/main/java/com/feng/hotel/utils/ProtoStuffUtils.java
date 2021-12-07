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

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Proto Stuff 序列化工具类
 */
public final class ProtoStuffUtils {

    /**
     * 类的Schema文件映射，即缓存
     */
    private static final Map<Class<?>, Schema<?>> SCHEMA_MAP = new ConcurrentHashMap<>();

    /**
     * 获取RuntimeSchema，其中Schema为类的格式
     * 为了优化性能，使用了缓存，减少对Schema的创建
     *
     * @param clazz 需要进行定义的class
     * @param <T>   类泛型
     * @return Schema文件
     */
    private static <T> Schema<T> getSchema(Class<T> clazz) {
        //noinspection unchecked
        Schema<T> schema = (Schema<T>) SCHEMA_MAP.get(clazz);
        if (schema == null) {
            schema = RuntimeSchema.getSchema(clazz);
            SCHEMA_MAP.put(clazz, schema);
        }
        return schema;
    }

    /**
     * 将对象序列化为二进制数组
     *
     * @param obj 需要序列化的对象
     * @param <T> 类泛型
     * @return 序列化后的二进制数组
     */
    public static <T> byte[] serialize(T obj) {
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            //noinspection unchecked
            Schema<T> schema = getSchema((Class<T>) obj.getClass());
            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            throw new RuntimeException("serialize object to bytes error. class: '" + obj.getClass().getName() + "'", e);
        } finally {
            buffer.clear();
        }
    }

    /**
     * 对象进行反序列化
     *
     * @param data  序列化后的二进制数据
     * @param clazz 反序列化的类型
     * @param <T>   类泛型
     * @return 反序列化后的对象
     */
    public static <T> T deserialize(byte[] data, Class<T> clazz) {
        try {
            Schema<T> schema = getSchema(clazz);
            T result = JdkUtils.newInstance(clazz);
            ProtostuffIOUtil.mergeFrom(data, result, schema);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("deserialize data to object error. class: '" + clazz.getName() + "'", e);
        }
    }

    /**
     * 对对象进行克隆，即深拷贝
     *
     * @param obj 需要克隆的对象
     * @param <T> 类泛型
     * @return 重新拷贝对象，指向不同的物理内存
     */
    public static <T> T clone(T obj) {
        byte[] bytes = serialize(obj);
        //noinspection unchecked
        return (T) deserialize(bytes, obj.getClass());
    }

}
