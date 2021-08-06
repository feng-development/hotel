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
package com.feng.hotel.config.mybatis;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * exist方法的实现
 *
 * @author asheng
 * @since 2020/8/17
 */
public class MybatisExistMethod extends AbstractMethod {

    /**
     * sql模版
     */
    private static final String SQL_TEMPLATE = "<script>select if(count(%s) > 0, true, false) from %s %s limit 1</script>";

    /**
     * sql查询对应的id
     */
    private static final String METHOD_NAME = "exist";

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass,
        TableInfo tableInfo) {
        String tableName = tableInfo.getTableName();
        String idColumn = tableInfo.getKeyColumn();
        String sql = String.format(SQL_TEMPLATE, idColumn, tableName, "${ew.customSqlSegment}");
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this
            .addSelectMappedStatementForOther(mapperClass, METHOD_NAME, sqlSource, Boolean.class);
    }
}
