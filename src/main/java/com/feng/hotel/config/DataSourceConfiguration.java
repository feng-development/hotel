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
package com.feng.hotel.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.feng.hotel.config.mybatis.MybatisSqlInjector;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

/**
 * 数据源配置，其中有数据源的读写分离
 * 只用配置文件中spring.datasource.enable开启的时候，才会进行配置数据源
 *
 * @author asheng
 * @since 2019/10/16
 */
@ConditionalOnProperty(name = "spring.datasource.enable", havingValue = "true")
@Configuration
public class DataSourceConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfiguration.class);

    /**
     * 用于写操作的数据源
     *
     * @return {@link DruidDataSource}
     */
    @Bean(name = "originWriter")
    @ConfigurationProperties(prefix = "spring.datasource.writer")
    @ConditionalOnProperty(name = "spring.datasource.enable", havingValue = "true")
    public DataSource originWriter() {
        LOGGER.info("writer datasource init success !!!");
        return new DruidDataSource();
    }

    /**
     * 用于读操作的数据源
     *
     * @return {@link DruidDataSource}
     */
    @Bean(name = "originReader")
    @ConfigurationProperties(prefix = "spring.datasource.reader")
    @ConditionalOnProperty(name = "spring.datasource.enable", havingValue = "true")
    public DataSource originReader() {
        LOGGER.info("reader datasource init success !!!");
        return new DruidDataSource();
    }

    /**
     * 动态数据源，根绝切面进行不同的数据源切换
     *
     * @param writerDataSource 用于写操作的数据源
     * @param readerDataSource 用于读操作的数据源
     * @return 根据切面区分使用不同的数据源
     */
    @Bean
    @Primary
    @DependsOn(value = {"originWriter", "originReader"})
    @ConditionalOnProperty(name = "spring.datasource.enable", havingValue = "true")
    public DataSource dynamicDataSource(@Qualifier("originWriter") DataSource writerDataSource,
        @Qualifier("originReader") DataSource readerDataSource) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.WRITER, writerDataSource);
        targetDataSources.put(DataSourceType.READER, readerDataSource);
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(writerDataSource);
        return dynamicDataSource;
    }

    /**
     * 分页插件
     *
     * @return {@link PaginationInterceptor}
     */
    @Bean
    @ConditionalOnBean(value = DataSource.class)
    @ConditionalOnMissingBean(value = PaginationInterceptor.class)
    public PaginationInterceptor paginationInterceptor() {
        LOGGER.info("mybatis plus pagination interceptor init success !!!");
        return new PaginationInterceptor();
    }

    @Bean
    @ConditionalOnBean(value = DataSource.class)
    public MybatisSqlInjector mybatisEvisionSqlInjector() {
        return new MybatisSqlInjector();
    }

    /**
     * 数据源切面类
     * <p>
     * 1.对系统的mapper层进行切面
     * 2.如果方法名使用以下字符开头使用读数据源
     * {@code query} {@code find} {@code get} {@code select} {@code exist} {@code is}
     *
     * @author asheng
     * @since 2019/10/16
     */
    @Aspect
    @Component
    @ConditionalOnProperty(name = "spring.datasource.enable", havingValue = "true")
    public static class DataSourceAspect {

        @Before("execution(* com..service..*.*(..))")
        public void before(JoinPoint point) {
            String methodName = point.getSignature().getName();
            if (isQuery(methodName)) {
                DataSourceHolder.setDataSourceType(DataSourceType.READER);
            } else {
                DataSourceHolder.setDataSourceType(DataSourceType.WRITER);
            }
        }

        @After("execution(* com..service..*.*(..))")
        public void after() {
            DataSourceHolder.clear();
        }

        /**
         * 判断切面的方法是否是查询方法
         *
         * @param methodName 方法名
         * @return true: 是查询方法  false: 不是查询方法
         */
        private Boolean isQuery(String methodName) {
            return StringUtils.startsWithAny(methodName,
                new String[]{"query", "find", "get", "select", "exist", "is", "list"});
        }
    }

    /**
     * 动态数据源，决定使用哪个数据源
     *
     * @author asheng
     * @since 2019/10/16
     */
    private static class DynamicDataSource extends AbstractRoutingDataSource {

        @Override
        protected Object determineCurrentLookupKey() {
            return DataSourceHolder.getDataSourceType();
        }
    }

    /**
     * 不同线程对数据源类型的持有
     *
     * @author asheng
     * @since 2019/10/16
     */
    private static class DataSourceHolder {

        private static final ThreadLocal<DataSourceType> HOLDER = new ThreadLocal<>();

        static void setDataSourceType(DataSourceType type) {
            HOLDER.set(type);
        }

        static DataSourceType getDataSourceType() {
            return HOLDER.get();
        }

        private static void clear() {
            HOLDER.remove();
        }
    }

    /**
     * 数据源类型
     *
     * @author asheng
     * @since 2019/10/16
     */
    private enum DataSourceType {
        WRITER, READER
    }

}
