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

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ReflectPermission;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * @author asheng
 * @since 2019/4/19
 */
public final class JdkUtils {

    private JdkUtils() {
    }

    /** 原始类型包装类和原始类型的映射 */
    private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_CLASS_MAP = new HashMap<>();

    static {
        PRIMITIVE_WRAPPER_CLASS_MAP.put(Boolean.class, boolean.class);
        PRIMITIVE_WRAPPER_CLASS_MAP.put(Character.class, char.class);
        PRIMITIVE_WRAPPER_CLASS_MAP.put(Byte.class, byte.class);
        PRIMITIVE_WRAPPER_CLASS_MAP.put(Short.class, short.class);
        PRIMITIVE_WRAPPER_CLASS_MAP.put(Integer.class, int.class);
        PRIMITIVE_WRAPPER_CLASS_MAP.put(Long.class, long.class);
        PRIMITIVE_WRAPPER_CLASS_MAP.put(Float.class, float.class);
        PRIMITIVE_WRAPPER_CLASS_MAP.put(Double.class, double.class);
    }

    /**
     * 判断是否是原始类型
     *
     * @param clazz class
     * @return boolean：原始类型 false：不是原始类型
     */
    public static boolean isPrimitive(Class<?> clazz) {
        return PRIMITIVE_WRAPPER_CLASS_MAP.containsKey(clazz) || PRIMITIVE_WRAPPER_CLASS_MAP.containsValue(clazz);
    }

    /**
     * 是否是void类型
     *
     * @param clazz class
     * @return true: void类型 false: 不是void类型
     */
    public static boolean isVoid(Class<?> clazz) {
        return clazz.equals(Void.class) || clazz.equals(void.class);
    }

    /**
     * 包装类型转普通的原始类型
     * Integer -> int
     *
     * @param wrapperClazz 包装的原始类型
     * @return 解封装后的原始类型
     */
    public static Class<?> convertToPrimitive(Class<?> wrapperClazz) {
        return PRIMITIVE_WRAPPER_CLASS_MAP.get(wrapperClazz);
    }

    /**
     * 获取classLoader
     * 1. 首先获取当前线程的classLoader
     * 2. 然后获取加载此类的classloader
     *
     * @param clazz class
     * @return classLoader
     */
    public static ClassLoader getClassLoader(Class<?> clazz) {
        ClassLoader loader = null;
        try {
            loader = Thread.currentThread().getContextClassLoader();
        } catch (Throwable e) {
            // do nothing
        }
        if (loader == null) {
            loader = clazz.getClassLoader();
        }

        return loader;
    }

    /**
     * 读取classpath下的properties文件
     *
     * @param path 文件位置
     * @return {@link Properties}
     */
    public static Properties getClasspathProperties(String path) {
        try {
            InputStream in = JdkUtils.getClassLoader(JdkUtils.class).getResourceAsStream(path);
            Properties properties = new Properties();
            properties.load(in);
            return properties;
        } catch (Exception e) {
            throw new RuntimeException("read class path properties error. path '" + path + "'");
        }
    }

    /**
     * 检查field方法以及method方法有没有访问的权限
     * 当对Field以及Method私有方法修改为公共方法的时候，需要调用这个方法进行检查
     *
     * @return true: 有权限访问  false: 无权限访问
     */
    public static boolean isMemberAccessible() {
        try {
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkPermission(new ReflectPermission("suppressAccessChecks"));
            }
        } catch (SecurityException e) {
            return false;
        }
        return true;
    }

    /**
     * 调用字段的get方法，获取字段的属性值
     *
     * @param object 对象，即字段所属类的实例化对象
     * @param field  字段
     * @return 字段的属性值
     */
    public static Object invokeGetter(Object object, Field field) {
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            boolean accessible = isMemberAccessible();
            if (accessible) {
                field.setAccessible(true);
                try {
                    return field.get(object);
                } catch (IllegalAccessException e1) {
                    throw new RuntimeException("have no privilege with '" + field.getName() + "'");
                }
            }
        }
        throw new RuntimeException("have no privilege with '" + field.getName() + "'");
    }

    /**
     *
     * 调用字段的setter方法
     *
     * @param object    对象，即字段所属类的实例化对象
     * @param field     字段
     * @param value     注入值
     */
    public static void invokeSetter(Object object, Field field, Object value) {
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            boolean accessible = isMemberAccessible();
            if (accessible) {
                field.setAccessible(true);
                try {
                    field.set(object, value);
                } catch (IllegalAccessException e1) {
                    throw new RuntimeException("have no privilege with '" + field.getName() + "'");
                }
            }
        }
    }

    /**
     * 调用方法，获取方法的返回值
     *
     * @param object 对象，即方法所属类的实例化对象
     * @param method 方法
     * @param params 参数
     * @return 方法返回值
     */
    public static Object invoke(Object object, Method method, Object ... params) {
        try {
            return method.invoke(object, params);
        } catch (IllegalAccessException e) {
            boolean accessible = isMemberAccessible();
            if (accessible) {
                method.setAccessible(true);
                try {
                    return method.invoke(object);
                } catch (IllegalAccessException e1) {
                    throw new RuntimeException("have no privilege with '" + method.getName() + "'");
                } catch (InvocationTargetException e1) {
                    throw new RuntimeException(e1);
                }
            }
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("have no privilege with '" + method.getName() + "'");
    }


    /**
     * 根据字段获取相应的getter方法
     *
     * @param field 字段
     * @return 对应getter方法名
     */
    public static String resolveGetterMethodName(Field field) {
        String fieldName = field.getName();
        Class<?> fieldType = field.getType();

        String prefix = fieldType.equals(Boolean.class) || fieldType.equals(boolean.class) ? "is" : "get";

        return prefix + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }


    /**
     * 获取getter方法
     *
     * @param clazz 需要获取的方法的类
     * @param field 该类下的字段
     * @return 该field对应的方法
     */
    public static Method resolveGetterMethod(Class<?> clazz, Field field) {
        String methodName = resolveGetterMethodName(field);
        try {
            return clazz.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("get declared method with class '" + clazz.getName()
                    + "' and method name '" + methodName + "' is not exist.", e);
        }
    }

    /**
     * 使用class默认的构造方法，初始化对象
     *
     * @param clazz 需要初始化的类
     * @param <T>   类的范型
     * @return 初始化的对象
     */
    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("class new instance error. there must be one default constructor with no args. " +
                    "class '" + clazz.getName() + "'.", e);
        }
    }

    /**
     * 判断方式否是get方法
     *
     * @param method 方法
     * @return {@code true: 属于get方法} {@code false: 不属于get方法}
     */
    public static boolean isGetterMethod(Method method) {
        if (method.getParameterCount() > 0) {
            return false;
        }

        Class<?> returnType = method.getReturnType();
        if (isVoid(returnType)) {
            return false;
        }

        String methodName = method.getName();
        return method.getName().startsWith("get") ||
            (methodName.startsWith("is") && (Objects.equals(returnType, boolean.class) || Objects.equals(returnType, Boolean.class)));
    }

}
