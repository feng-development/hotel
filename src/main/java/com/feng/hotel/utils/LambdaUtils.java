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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feng.hotel.base.Pagination;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.apache.commons.lang.StringUtils;
import org.springframework.lang.NonNull;

/**
 * Lambda函数工具类
 *
 * @author asheng
 * @since 2019/10/22
 */
public final class LambdaUtils {

    /**
     * 获取结果，首先通过缓存获取，缓存不存在通过db获取，然后进行缓存、
     * 主要是在redis缓存的地方使用
     *
     * @param cacheSelector 缓存获取器
     * @param dbSelector    数据库获取器
     * @param cacheConsumer 数据消费器
     * @return 获取的结果
     */
    public static <T> T fetchResult(Supplier<T> cacheSelector, Supplier<T> dbSelector, Consumer<T> cacheConsumer) {
        if (Objects.isNull(cacheSelector)) {
            throw new NullPointerException("cache selector is null");
        }

        if (Objects.isNull(dbSelector)) {
            throw new NullPointerException("database selector is null");
        }

        T result = cacheSelector.get();
        if (result == null) {
            result = dbSelector.get();
            if (cacheConsumer != null) {
                cacheConsumer.accept(result);
            }
        }

        return result;
    }

    /**
     * 增强MybatisPlus的LambdaQueryWrapper功能
     * 1.如果只有一个元素的时候，将in语句转换成为eq语句
     * 2.如果有多个元素的时候，使用in语句
     *
     * @param sFunction 函数
     * @param coll      集合
     * @return LambdaQueryWrapper
     */
    public static <T, R> LambdaQueryWrapper<T> lambdaQuery(SFunction<T, R> sFunction, @NonNull Collection<?> coll) {
        if (CollectionUtils.isEmpty(coll)) {
            throw new RuntimeException("input elements can not be null. otherwise scan all tables");
        }

        if (coll.size() > 1) {
            return new LambdaQueryWrapper<T>().in(sFunction, coll);
        }

        if (coll instanceof List) {
            return new LambdaQueryWrapper<T>().eq(sFunction, CollectionUtils.firstElement((List<?>) coll));
        }

        if (coll instanceof Set) {
            return new LambdaQueryWrapper<T>().eq(sFunction, CollectionUtils.firstElement((Set<?>) coll));
        }

        throw new UnsupportedOperationException("current only support List or Set");
    }

    /**
     * 判断是否为空，不为空则调用consumer函数
     *
     * @param param    参数
     * @param consumer 不为空时的处理函数
     */
    public static <P> void lambdaQuery(P param, Consumer<P> consumer) {
        if (Objects.isNull(param)) {
            return;
        }

        if ((param instanceof String) && StringUtils.isBlank((String) param)) {
            return;
        }

        if ((param instanceof Collection) && CollectionUtils.isEmpty((Collection<?>) param)) {
            return;
        }

        if ((param instanceof Map) && CollectionUtils.isEmpty((Map<?, ?>) param)) {
            return;
        }

        consumer.accept(param);
    }

    /**
     * 讲一个集合转换为另一种集合类型
     *
     * @param coll     输入集合
     * @param function 转换函数
     * @return 转换后的集合
     */
    public static <T, E> Collection<E> list(Collection<T> coll, Function<Collection<T>, Collection<E>> function) {
        if (coll == null || coll.isEmpty()) {
            return Collections.emptyList();
        }

        return function.apply(coll);
    }

    /**
     * 将MybatisPlus的IPage对象转换成为Pagination对象
     *
     * @param page     Mybatis的分页对象
     * @param function 转换函数
     * @return 转换后的分页对象
     */
    public static <T, E> Pagination<E> page(IPage<T> page, Function<List<T>, Collection<E>> function) {
        if (page == null || page.getTotal() == 0L) {
            return Pagination.empty();
        }

        if (CollectionUtils.isEmpty(page.getRecords())) {
            return Pagination.page((int) page.getTotal(), Collections.emptyList());
        }

        Collection<E> result = function.apply(page.getRecords());
        return Pagination.page((int) page.getTotal(), result);
    }

    /**
     * 将Spring的Page对象转换成为Pagination对象
     *
     * @param page     Mybatis的分页对象
     * @param function 转换函数
     * @return 转换后的分页对象
     */
    public static <T, E> Pagination<E> page(Page<T> page, Function<List<T>, Collection<E>> function) {
        if (page == null || page.getTotal() == 0L) {
            return Pagination.empty();
        }

        if (CollectionUtils.isEmpty(page.getRecords())) {
            return Pagination.page((int) page.getTotal(), Collections.emptyList());
        }

        Collection<E> result = function.apply(page.getRecords());
        return Pagination.page((int) page.getTotal(), result);
    }

    /**
     * 将Pagination对象转换为另一种java对象的Pagination
     *
     * @param page     分页对象
     * @param function 转换函数
     * @return 转换后的分页对象
     */
    public static <T, E> Pagination<E> page(Pagination<T> page, Function<List<T>, Collection<E>> function) {
        if (page == null || page.isEmpty()) {
            return Pagination.empty();
        }

        if (CollectionUtils.isEmpty(page.getResult())) {
            return Pagination.page(page.getTotal(), Collections.emptyList());
        }

        Collection<E> result = function.apply(page.getResult());
        return Pagination.page(page.getTotal(), result);
    }

    /**
     * 将Collection按照某个属性作为key转成map
     * Note:注意和Stream.toCollector(Collector.toMap)的区别，主要是增强Collector.toMap的空指针问题
     *
     * @param coll     输入集合
     * @param function 转换函数
     * @param <T>      目标类型
     * @param <R>      结果类型，即key的类型
     * @return 转换后的Map
     */
    public static <T, R> Map<R, T> map(@NonNull Collection<T> coll, Function<T, R> function) {
        if (CollectionUtils.isEmpty(coll)) {
            return Collections.emptyMap();
        }

        Map<R, T> map = new HashMap<>();
        coll.forEach(t -> {
            if (t == null) {
                map.put(null, null);
            } else {
                R apply = function.apply(t);
                map.put(apply, t);
            }
        });

        return map;
    }

    /**
     * 将Collection按照某个属性作为Key, 某个属性作为value转为map
     * Note:注意和Stream.toCollector(Collector.toMap)的区别，主要是增强Collector.toMap的空指针问题
     *
     * @param coll    输入集合
     * @param keyFunc Key转换函数
     * @param valFunc Val转换函数
     * @param <T>     目标类型
     * @param <R>     Key类型
     * @param <E>     Val类型
     * @return 转换后的Map
     */
    public static <T, R, E> Map<R, E> map(@NonNull Collection<T> coll, Function<T, R> keyFunc, Function<T, E> valFunc) {
        if (CollectionUtils.isEmpty(coll)) {
            return Collections.emptyMap();
        }

        Map<R, E> map = new HashMap<>();

        coll.forEach(t -> {
            if (t == null) {
                map.put(null, null);
            } else {
                R key = keyFunc.apply(t);
                E val = valFunc.apply(t);
                map.put(key, val);
            }
        });

        return map;
    }

    /**
     * 将Collection按照某个属性作为Key, 某个属性作为value转为map
     * Note:注意和Stream.toCollector(Collector.toMap)的区别，主要是增强Collector.toMap的空指针问题
     *
     * @param coll    输入集合
     * @param keyFunc Key转换函数
     * @param valFunc Val转换函数
     * @param <T>     目标类型
     * @param <R>     Key类型
     * @param <E>     Val类型
     * @return 转换后的Map
     */
    public static <T, R, E> Map<R, List<E>> mapList(@NonNull Collection<T> coll, Function<T, R> keyFunc, Function<T, E> valFunc) {
        if (CollectionUtils.isEmpty(coll)) {
            return Collections.emptyMap();
        }

        Map<R, List<E>> map = new HashMap<>();

        coll.forEach(t -> {
            if (t == null) {
                map.put(null, null);
            } else {
                R key = keyFunc.apply(t);
                E val = valFunc.apply(t);

                List<E> rs = map.get(key);
                if (rs == null) {
                    rs = new ArrayList<>();
                }
                rs.add(val);
                map.put(key, rs);
            }
        });

        return map;
    }

}
