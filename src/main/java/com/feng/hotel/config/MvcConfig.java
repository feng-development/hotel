package com.feng.hotel.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.feng.hotel.base.Constants;
import com.feng.hotel.base.ParamType;
import com.feng.hotel.base.exception.DateConverterException;
import com.feng.hotel.common.serializer.Date2StringSerializer;
import com.feng.hotel.utils.date.DatePattern;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author Administrator
 * @since 2021/8/6
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private final static Logger LOGGER = LoggerFactory.getLogger(MvcConfig.class);
    @Value(value = "${exclude.path}")
    private List<String> excludePath;
    private static final ModelRef STR_MODEL_REF = new ModelRef(String.class.getSimpleName());

    private static final Map<Pattern, DatePattern> PATTERN_MAP = new HashMap<>();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(new TokenInterceptor());
        registration.addPathPatterns("/**");                      //所有路径都被拦截
        registration.excludePathPatterns(excludePath);
    }


    @Bean
    public Docket createRestApi() {
        List<Parameter> parameters = Stream.of(Constants.AUTH, Constants.SIGN)
            .map(key -> new ParameterBuilder()
                .name(key)
                .description(key)
                .modelRef(STR_MODEL_REF)
                .parameterType(ParamType.HEADER)
                .build())
            .collect(Collectors.toList());

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(new ApiInfoBuilder()
                .title("hotel")
                .termsOfServiceUrl("http://localhost:7014/swagger-ui.html")
                .version("1.0")
                .build())
            .select()
            .apis(RequestHandlerSelectors.basePackage(Constants.BASE_PACKAGE))
            .paths(PathSelectors.any())
            .build()
            .globalOperationParameters(parameters);

        LOGGER.info("swagger init success !!!");

        return docket;
    }

    /**
     * 使用FastJson作为HttpMessageConverter进行返回json数据
     * 1. 处理long类型为string类型进行传递，避免出现long类型精度丢失问题
     * 2. 处理Date类型为string类型的事件戳，首先是时间戳，其次避免丢失精度
     * <p>
     * 处理前先将Jackson的处理器全部移除掉，然后使用FastJson，其中也要支持stream流用于监控
     *
     * @param converters {@link HttpMessageConverter}
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.removeIf(converter -> converter instanceof MappingJackson2HttpMessageConverter);
        converters.add(buildConverter());
        LOGGER.info("fastJson converter init success !!!");
    }

    /**
     * 构造FastJsonHttpMessageConverter
     *
     * @return {@link FastJsonHttpMessageConverter}
     */
    public static FastJsonHttpMessageConverter buildConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();

        config.setSerializerFeatures(
            SerializerFeature.WriteMapNullValue,
            SerializerFeature.DisableCircularReferenceDetect,
            SerializerFeature.WriteNullStringAsEmpty,
            SerializerFeature.UseISO8601DateFormat
        );

        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.put(BigInteger.class, ToStringSerializer.instance);
        serializeConfig.put(Long.class, ToStringSerializer.instance);
        serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
        serializeConfig.put(Date.class, Date2StringSerializer.instance);

        config.setSerializeConfig(serializeConfig);

        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8, MediaType.APPLICATION_JSON, new MediaType("application", "*+json")));

        converter.setFastJsonConfig(config);

        return converter;
    }

    /**
     * 日期格式化，对前段输入的Date类型进行转换
     *
     * @author asheng
     * @since 2019/10/24
     */
    private static class DateFormat implements Formatter<Date> {

        /**
         * 全局单例
         */
        public static final DateFormat INSTANCE = new DateFormat();

        private DateFormat() {
        }

        @Override
        public @NonNull
        Date parse(@NonNull String input, @NonNull Locale locale) {
            if (StringUtils.isBlank(input)) {
                throw new DateConverterException(input);
            }

            try {
                if (Constants.NUMBER_PATTERN.matcher(input).matches()) {
                    long inputTimestamp = Long.parseLong(input);
                    if (System.currentTimeMillis() / inputTimestamp >= 1000L) {
                        inputTimestamp = TimeUnit.SECONDS.toMillis(inputTimestamp);
                    }
                    return new Date(inputTimestamp);
                }
            } catch (Exception e) {
                LOGGER.error("parse input to java.util.Date error. input: '" + input + "'", e);
            }

            for (Map.Entry<Pattern, DatePattern> entry : PATTERN_MAP.entrySet()) {
                if (entry.getKey().matcher(input).matches()) {
                    return entry.getValue().parse(input);
                }
            }
            throw new DateConverterException(input);
        }

        @Override
        public @NonNull
        String print(@NonNull Date date, @NonNull Locale locale) {
            return " input type is 'java.util.Date' and timestamp is '" + date.getTime() + "'";
        }
    }

    /**
     * 增加格式处理器，增加日期格式处理器，对java.util.Date类型进行处理
     * 对日期进行反序列化对
     *
     * @param registry {@link FormatterRegistry}
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(DateFormat.INSTANCE);
    }

}
