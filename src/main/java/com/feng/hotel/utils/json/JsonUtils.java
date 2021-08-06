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
package com.feng.hotel.utils.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.util.TypeUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;

/**
 * json工具类
 *
 * @author asheng
 * @since 2019/1/15
 */
public final class JsonUtils {

    private JsonUtils() {
    }

    /**
     * 把对象Json化成String
     *
     * @param t 需要转化的对象
     * @return Json化后的字符串
     */
    public static <T> String serialize(T t) {
        return JSONObject.toJSONString(t);
    }

    /**
     * 把Json串反序列化成对象
     *
     * @param str   Json串
     * @param clazz 要实例化的类
     * @return 反序列化后的对象
     */
    public static <T> T deserialize(String str, Class<T> clazz) {
        if (StringUtils.isBlank(str)) {
            return null;
        }

        return JSONObject.toJavaObject(JSONObject.parseObject(str), clazz);
    }

    /**
     * 把Json串反序列化成对象
     *
     * @param str      Json串
     * @param function 转换函数
     * @return 反序列化后的对象
     */
    public static <T> T deserialize(String str, Function<String, T> function) {
        if (StringUtils.isBlank(str)) {
            return null;
        }

        return function.apply(str);
    }

    /**
     * 增强对FastJson的处理，对范型类对处理
     *
     * @param str  json串
     * @param type 类型
     * @return 该范型类对象
     */
    public static <T> T deserialize(String str, TypeReference<T> type) {
        if (StringUtils.isBlank(str)) {
            return null;
        }

        return JSONObject.parseObject(str, type);
    }

    /**
     * 把JSON反序列化成对象，如果实例化对象为空，则new一个
     *
     * @param str   json串
     * @param clazz 需要实例化的对象
     * @return 实例化后的对象
     */
    public static <T> T deserializeNotNull(String str, Class<T> clazz) {
        T deserialize = deserialize(str, clazz);
        if (deserialize == null) {
            try {
                deserialize = clazz.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(
                    "clazz with type '" + clazz.getName() + "' init failed.");
            }
        }
        return deserialize;
    }

    /**
     * 把Json数组串反序列化成数组对象
     *
     * @param str   Json数组串
     * @param clazz 需要实例化的类
     * @param <T>   范型对象类型
     * @return 反序列化后的数组对象
     */
    public static <T> List<T> deserializeArray(String str, Class<T> clazz) {
        JSONArray array = JSONObject.parseArray(str);
        if (array == null || array.isEmpty()) {
            return null;
        }
        return convertList(array, clazz);
    }

    /**
     * 把Json数组串反序列化成数组对象
     *
     * @param str      Json数组串
     * @param function 转换函数
     * @param <T>      范型对象类型
     * @return 反序列化后的数组对象
     */
    public static <T> List<T> deserializeArray(String str, Function<String, List<T>> function) {
        if (StringUtils.isBlank(str)) {
            return null;
        }

        return function.apply(str);
    }

    /**
     * 将一个对象转成另一个对象
     *
     * @param base   需要转的对象
     * @param target 转换成的目标对象类型
     * @return 目标对象
     */
    public static <T> T convert(Object base, Class<T> target) {
        String json = JsonUtils.serialize(base);
        return JsonUtils.deserialize(json, target);
    }

    /**
     * 转换List对象
     *
     * @param base   需要转化的对象
     * @param target 目标对象
     * @return 目标集合
     */
    public static <B, T> List<T> convertList(Collection<B> base, Class<T> target) {
        if (base == null || base.isEmpty()) {
            return Collections.emptyList();
        }

        return base.stream().map(n -> convert(n, target)).collect(Collectors.toList());
    }

    /**
     * 转换List对象
     *
     * @param base     需要转化的对象
     * @param function 转换函数
     * @return 目标集合
     */
    public static <B, T> List<T> convertList(Collection<B> base,
        Function<Collection<B>, List<T>> function) {
        if (base == null || base.isEmpty()) {
            return Collections.emptyList();
        }
        return function.apply(base);
    }

    /**
     * 转换Map对象，将Map的Value转成另一种类型
     * Note: 健值类型不能转换
     *
     * @param baseMap 需要转换的map对象
     * @param target  目标对象
     * @param <K>     key类型
     * @param <V>     转换对象类型
     * @param <E>     目标对象类型
     * @return 转换后的Map对象
     */
    public static <K, V, E> Map<K, E> convertMap(Map<K, V> baseMap, Class<E> target) {
        if (baseMap == null || baseMap.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<K, E> targetMap = new HashMap<>();
        Set<Map.Entry<K, V>> entries = baseMap.entrySet();

        entries.forEach(entry -> targetMap.put(entry.getKey(), convert(entry.getValue(), target)));

        return targetMap;
    }

    /**
     * 转换Map对象，将Map的Value转成另一种类型
     * Note: 健值类型不能转换
     *
     * @param baseMap  需要转换的map对象
     * @param function 转换函数
     * @param <K>      key类型
     * @param <V>      转换对象类型
     * @param <E>      目标对象类型
     * @return 转换后的Map对象
     */
    public static <K, V, E> Map<K, E> convertMap(Map<K, V> baseMap, Function<V, E> function) {
        if (baseMap == null || baseMap.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<K, E> targetMap = new HashMap<>();
        Set<Map.Entry<K, V>> entries = baseMap.entrySet();

        entries.forEach(entry -> targetMap.put(entry.getKey(), function.apply(entry.getValue())));
        return targetMap;
    }

    /**
     * 获取JSON串中某属性的int值
     * <pre>
     *     {
     *         "name":"asheng",
     *         "age:12,
     *         "gender":"male",
     *         "interests:[
     *             "singe", "play game"
     *         ],
     *         "family":{
     *             "wife":{
     *                 "name":"asheng1",
     *                 "age":12
     *             },
     *             "son":{
     *                 "name":"asheng2",
     *                 "age":1
     *             }
     *         }
     *     }
     * </pre>
     * <p>
     * 获取SON属性的年龄
     * <code>
     * int age = JsonUtils.getIntValue("family.son.age")
     * </code>
     *
     * @param input 输入JSON串
     * @param key   获取的属性，多层关系使用"."分割，{@code "family.son.age"}
     * @return 获取的具体int类型属性值
     */
    public static int getIntValue(JSONObject input, String key) {
        JsonFieldTokenizer tokenizer = new JsonFieldTokenizer(key);
        Object result = resolve(input, tokenizer);
        Integer value = TypeUtils.castToInt(result);
        return Objects.isNull(value) ? 0 : value;
    }

    /**
     * 获取JSON串中某属性的int值
     * <pre>
     *     {
     *         "name":"asheng",
     *         "age:12,
     *         "gender":"male",
     *         "interests:[
     *             "singe", "play game"
     *         ],
     *         "family":{
     *             "wife":{
     *                 "name":"asheng1",
     *                 "age":12
     *             },
     *             "son":{
     *                 "name":"asheng2",
     *                 "age":1
     *             }
     *         }
     *     }
     * </pre>
     * <p>
     * 获取SON属性的年龄
     * <code>
     * long age = JsonUtils.getLongValue("family.son.age")
     * </code>
     *
     * @param input 输入JSON串
     * @param key   获取的属性，多层关系使用"."分割，{@code "family.son.age"}
     * @return 获取的具体long类型属性值
     */
    public static long getLongValue(JSONObject input, String key) {
        JsonFieldTokenizer tokenizer = new JsonFieldTokenizer(key);
        Object result = resolve(input, tokenizer);
        Long value = TypeUtils.castToLong(result);
        return Objects.isNull(value) ? 0L : value;
    }

    /**
     * 获取JSON串中某属性的boolean值
     * <pre>
     *     {
     *         "name":"asheng",
     *         "age:12,
     *         "gender":"male",
     *         "interests:[
     *             "singe", "play game"
     *         ],
     *         "family":{
     *             "wife":{
     *                 "name":"asheng1",
     *                 "age":12
     *             },
     *             "son":{
     *                 "name":"asheng2",
     *                 "age":1,
     *                 "canRun":false
     *             }
     *         }
     *     }
     * </pre>
     * <p>
     * 获取SON属性的是否能跑步的属性
     * <code>
     * boolean canRun = JsonUtils.getBooleanValue("family.son.canRun")
     * </code>
     *
     * @param input 输入JSON串
     * @param key   获取的属性，多层关系使用"."分割，{@code "family.son.canRun"}
     * @return 获取的具体boolean类型属性值
     */
    public static boolean getBooleanValue(JSONObject input, String key) {
        JsonFieldTokenizer tokenizer = new JsonFieldTokenizer(key);
        Object result = resolve(input, tokenizer);
        Boolean value = TypeUtils.castToBoolean(result);
        return Objects.isNull(value) ? false : value;
    }

    /**
     * 获取JSON串中某属性的String值
     * <pre>
     *     {
     *         "name":"asheng",
     *         "age:12,
     *         "gender":"male",
     *         "interests:[
     *             "singe", "play game"
     *         ],
     *         "family":{
     *             "wife":{
     *                 "name":"asheng1",
     *                 "age":12
     *             },
     *             "son":{
     *                 "name":"asheng2",
     *                 "age":1,
     *                 "canRun":false
     *             }
     *         }
     *     }
     * </pre>
     * <p>
     * 获取SON属性的姓名属性
     * <code>
     * String name = JsonUtils.getString("family.son.name")
     * </code>
     *
     * @param input 输入JSON串
     * @param key   获取的属性，多层关系使用"."分割，{@code "family.son.name"}
     * @return 获取的具体String类型属性值
     */
    public static String getStringValue(JSONObject input, String key) {
        JsonFieldTokenizer tokenizer = new JsonFieldTokenizer(key);
        Object result = resolve(input, tokenizer);
        return TypeUtils.castToString(result);
    }

    /**
     * 获取JSON串中某属性的double值
     * <pre>
     *     {
     *         "name":"asheng",
     *         "age:12,
     *         "gender":"male",
     *         "interests:[
     *             "singe", "play game"
     *         ],
     *         "family":{
     *             "wife":{
     *                 "name":"asheng1",
     *                 "age":12
     *             },
     *             "son":{
     *                 "name":"asheng2",
     *                 "age":1,
     *                 "canRun":false,
     *                 "score":3.5
     *             }
     *         }
     *     }
     * </pre>
     * <p>
     * 获取SON的分数属性
     * <code>
     * double score = JsonUtils.getDouble("family.son.score")
     * </code>
     *
     * @param input 输入JSON串
     * @param key   获取的属性，多层关系使用"."分割，{@code "family.son.score"}
     * @return 获取的具体double类型属性值
     */
    public static double getDoubleValue(JSONObject input, String key) {
        JsonFieldTokenizer tokenizer = new JsonFieldTokenizer(key);
        Object result = resolve(input, tokenizer);
        Double value = TypeUtils.castToDouble(result);
        return Objects.isNull(value) ? 0D : value;
    }

    /**
     * 获取JSON串中某属性的float值
     * <pre>
     *     {
     *         "name":"asheng",
     *         "age:12,
     *         "gender":"male",
     *         "interests:[
     *             "singe", "play game"
     *         ],
     *         "family":{
     *             "wife":{
     *                 "name":"asheng1",
     *                 "age":12
     *             },
     *             "son":{
     *                 "name":"asheng2",
     *                 "age":1,
     *                 "canRun":false,
     *                 "score":3.5
     *             }
     *         }
     *     }
     * </pre>
     * <p>
     * 获取SON的分数属性
     * <code>
     * float score = JsonUtils.getFloatValue("family.son.score")
     * </code>
     *
     * @param input 输入JSON串
     * @param key   获取的属性，多层关系使用"."分割，{@code "family.son.score"}
     * @return 获取的具体float类型属性值
     */
    public static float getFloatValue(JSONObject input, String key) {
        JsonFieldTokenizer tokenizer = new JsonFieldTokenizer(key);
        Object result = resolve(input, tokenizer);
        Float value = TypeUtils.castToFloat(result);
        return Objects.isNull(value) ? 0F : value;
    }

    /**
     * 获取JSON串中某属性的JSONArray值
     * <pre>
     *     {
     *         "name":"asheng",
     *         "age:12,
     *         "gender":"male",
     *         "interests:[
     *             "singe", "play game"
     *         ],
     *         "family":{
     *             "wife":{
     *                 "name":"asheng1",
     *                 "age":12
     *             },
     *             "son":{
     *                 "name":"asheng2",
     *                 "age":1,
     *                 "canRun":false,
     *                 "score":3.5
     *             }
     *         }
     *     }
     * </pre>
     * <p>
     * 获取SON的分数属性
     * <code>
     * JSONArray interests = JsonUtils.getJsonArray("interests")
     * </code>
     *
     * @param input 输入JSON串
     * @param key   获取的属性，多层关系使用"."分割，{@code "interests"}
     * @return 获取的具体JSONArray类型属性值
     */
    public static JSONArray getJsonArray(JSONObject input, String key) {
        JsonFieldTokenizer tokenizer = new JsonFieldTokenizer(key);
        return (JSONArray) resolve(input, tokenizer);
    }

    /**
     * 获取JSON串中某属性的JSONObject值
     * <pre>
     *     {
     *         "name":"asheng",
     *         "age:12,
     *         "gender":"male",
     *         "interests:[
     *             "singe", "play game"
     *         ],
     *         "family":{
     *             "wife":{
     *                 "name":"asheng1",
     *                 "age":12
     *             },
     *             "son":{
     *                 "name":"asheng2",
     *                 "age":1,
     *                 "canRun":false,
     *                 "score":3.5
     *             }
     *         }
     *     }
     * </pre>
     * <p>
     * 获取SON的分数属性
     * <code>
     * JSONObject wife = JsonUtils.getJsonObject("family.wife")
     * </code>
     *
     * @param input 输入JSON串
     * @param key   获取的属性，多层关系使用"."分割，{@code "interests"}
     * @return 获取的具体JSONArray类型属性值
     */
    public static JSONObject getJsonObject(JSONObject input, String key) {
        JsonFieldTokenizer tokenizer = new JsonFieldTokenizer(key);
        return (JSONObject) resolve(input, tokenizer);
    }

    /**
     * 匹配JSON数组格式
     */
    public static final Pattern ARRAY_PATTERN = Pattern.compile("\\[\\d+]$");

    /**
     * 解析JSON串中，某个属性的具体值，返回格式为String类型，然后再进行具体的转换
     * <pre>
     *     {
     *         "name":"asheng",
     *         "age:12,
     *         "gender":"male",
     *         "interests:[
     *             "singe", "play game"
     *         ],
     *         "family":{
     *             "wife":{
     *                 "name":"asheng1",
     *                 "age":12
     *             },
     *             "son":{
     *                 "name":"asheng2",
     *                 "age":1
     *             }
     *         }
     *     }
     * </pre>
     * <p>
     * 解析SON中的年龄属性
     * <code>
     * JsonFieldTokenizer tokenizer = new JsonFieldTokenizer("family.son.age");
     * Integer age = (Integer) JsonUtils.resolve(JsonObject, tokenizer);
     * </code>
     *
     * @param input     输入JSON串
     * @param tokenizer JSON字段解析器
     * @return 获取的内容
     */
    public static Object resolve(JSONObject input, JsonFieldTokenizer tokenizer) {
        if (input == null) {
            return null;
        }
        String next = tokenizer.next();
        // resolve json array
        if (next.contains("[") && next.contains("]")) {
            int index = next.indexOf("[");
            String suffix = next.substring(index);
            if (ARRAY_PATTERN.matcher(suffix).matches()) {
                String prefix = next.substring(0, index);
                int arrayIndex = Integer.parseInt(suffix.substring(1, suffix.length() - 1));
                Object arrayObj = input.get(prefix);
                if (arrayObj == null) {
                    return null;
                }
                if (!(arrayObj instanceof JSONArray)) {
                    throw new RuntimeException("当前输入JSON串无法解析此属性[" + next + "].不能转换成为JSON数组");
                }
                JSONArray jsonArray = (JSONArray) arrayObj;
                Object nodeObj = jsonArray.get(arrayIndex);
                if (nodeObj == null) {
                    return null;
                }
                if (!tokenizer.hasNext()) {
                    return nodeObj;
                }
                if (!(nodeObj instanceof JSONObject)) {
                    throw new RuntimeException("当前输入JSON串无法解析此属性[" + next + "].不能转为JSON对象");
                }
                return resolve((JSONObject) nodeObj, tokenizer);
            }
        }
        // resolve json node
        if (!tokenizer.hasNext()) {
            return input.get(next);
        }

        Object nextObj = input.get(next);
        if (nextObj == null) {
            return null;
        }

        if (!(nextObj instanceof JSONObject)) {
            throw new RuntimeException("当前输入JSON串无法解析此属性[" + next + "].不能转为JSON对象");
        }
        JSONObject jsonNode = (JSONObject) nextObj;
        return resolve(jsonNode, tokenizer);
    }

    /**
     * Json字段解析器
     * <p>
     * 使用了迭代器模式进行迭代
     * 对多级的key进行迭代和解析
     * <pre>
     *     {
     *         "name":"asheng",
     *         "age:12,
     *         "gender":"male",
     *         "interests:[
     *             "singe", "play game"
     *         ],
     *         "family":{
     *             "wife":{
     *                 "name":"asheng1",
     *                 "age":12
     *             },
     *             "son":{
     *                 "name":"asheng2",
     *                 "age":1
     *             }
     *         }
     *     }
     * </pre>
     * <p>
     * 如果要解析该JSON串中的儿子某属性
     * <code>
     * JsonFieldTokenizer tokenizer = new JsonFieldTokenizer("family.son.age");
     * </code>
     */
    public static class JsonFieldTokenizer implements Iterator<String> {

        private final Iterator<String> iterator;

        public JsonFieldTokenizer(String key) {
            if (StringUtils.isBlank(key)) {
                throw new RuntimeException("待解析待JSON字段不能为空");
            }
            String[] split = key.split("\\.");
            this.iterator = Arrays.asList(split).iterator();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public String next() {
            return iterator.next();
        }
    }

}
