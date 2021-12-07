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
package com.feng.hotel.annotation;

import java.lang.annotation.*;

/**
 * 注释在父Id字段上，标示此字段为当前对象的父id字段
 * 目前此字段只支持string类型以及number类型
 *
 * @author asheng
 * @since 2019/10/14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface ParentIdField {
}
