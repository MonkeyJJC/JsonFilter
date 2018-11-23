package com.monkey.jsonfilter.configuration;

import com.monkey.jsonfilter.converter.JsonFilterHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @description: 自定义消息转换器的配置
 * 通过@Bean定义HttpMessageConverter是向项目中添加消息转换器最简便的办法，
 * 这类似于之前提到的添加Servlet Filters。如果Spring扫描到HttpMessageConverter类型的bean，就会将它自动添加到调用链中。
 * @author: JJC
 * @createTime: 2018/11/23
 */
@Configuration
public class FastJsonConfiguration {
    @Bean
    public HttpMessageConverters customConverters() {
        Collection<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        /**
         * 创建自定义消息转换器并加入Collection
         */
        JsonFilterHttpMessageConverter jsonFilterHttpMessageConverter = new JsonFilterHttpMessageConverter();
        messageConverters.add(jsonFilterHttpMessageConverter);
        return new HttpMessageConverters(true, messageConverters);


    }
}
