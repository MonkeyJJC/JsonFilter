package com.monkey.jsonfilter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 用于嵌套类情况的二层过滤（本质目的，为了可以二次执行）
 * @author: JJC
 * @createTime: 2018/11/26
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MultiSerializeField {

    Class clazz();

    /**
     * 需要返回的字段
     * @return
     */
    String[] includes() default {};

    /**
     * 需要去除的字段
     * @return
     */
    String[] excludes() default {};
}
