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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

/**
 * 对Spring的CollectionUtils类的增强
 *
 * @author asheng
 * @since 2019/10/23
 */
public final class CollectionUtils extends org.springframework.util.CollectionUtils {

    /**
     * 将Map所有的值存放到list中
     *
     * @param map map集合
     * @param <K> key的类型
     * @param <V> value的类型
     * @return 所有值的列表
     */
    public static <K, V> List<V> value(Map<K, List<V>> map) {
        if (isEmpty(map)) {
            return Collections.emptyList();
        }
        List<V> list = new ArrayList<>();
        map.forEach((k, v) -> {
            if (!isEmpty(v)) {
                list.addAll(v);
            }
        });
        return list;
    }

    /**
     * 将单个元素转成list
     *
     * @param element 元素
     * @return 转化后的list
     */
    public static <E> List<E> wrap(E element) {
        return element == null ? Collections.emptyList() : Collections.singletonList(element);
    }

    /**
     * 为list类型进行转换，如果为null，转成empty list
     *
     * @param list list
     * @return 转换后的list
     */
    public static <E> List<E> wrap(List<E> list) {
        return list == null ? Collections.emptyList() : list;
    }

    /**
     * 为set类型进行转换，如果为null，转成empty set
     *
     * @param set set
     * @return 转换后的set
     */
    public static <E> Set<E> wrap(Set<E> set) {
        return set == null ? Collections.emptySet() : set;
    }

    /**
     * 为map类型进行转换，如果为null，转成empty map
     *
     * @param map map
     * @return 转换后的map
     */
    public static <K, V> Map<K, V> wrap(Map<K, V> map) {
        return map == null ? Collections.emptyMap() : map;
    }

    /**
     * 获取list的第一个元素
     *
     * @param list list
     * @return 第一个元素
     */
    public static <E> E firstElement(List<E> list) {
        if (isEmpty(list)) {
            return null;
        }

        return list.get(0);
    }

    /**
     * 获取set的第一个元素
     *
     * @param set st
     * @return 第一个元素
     */
    public static <E> E firstElement(Set<E> set) {
        if (isEmpty(set)) {
            return null;
        }

        if (set instanceof SortedSet) {
            return ((SortedSet<E>) set).first();
        }

        Iterator<E> it = set.iterator();
        E last = null;
        if (it.hasNext()) {
            last = it.next();
        }
        return last;
    }

}
