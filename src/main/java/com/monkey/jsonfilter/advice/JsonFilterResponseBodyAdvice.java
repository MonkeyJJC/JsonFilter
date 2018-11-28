package com.monkey.jsonfilter.advice;

import com.monkey.jsonfilter.annotation.MoreSerializeField;
import com.monkey.jsonfilter.annotation.MultiSerializeField;
import com.monkey.jsonfilter.annotation.SerializeField;
import com.monkey.jsonfilter.bean.JsonFilterObject;
import com.monkey.jsonfilter.exception.IncludesAndExcludesConflictException;
import com.monkey.jsonfilter.result.ReturnResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @description: ResponseBodyAdvice是spring4.1的新特性，其作用是在响应体写出之前做一些处理；比如，修改返回值、加密等
 * @author: JJC
 * @createTime: 2018/11/23
 */
@ControllerAdvice
public class JsonFilterResponseBodyAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Class converterType) {
        return true;
    }

    /**
     *
     * @param object- the body to be written
     * @param methodParameter- the return type of the controller method
     * @param mediaType- the content type selected through content negotiation
     * @param converterType- the converter type selected to write to the response
     * @param serverHttpRequest- the current request
     * @param serverHttpResponse- the current response
     * 官方地址： https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/annotation/ResponseBodyAdvice.html
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object object, MethodParameter methodParameter, MediaType mediaType, Class converterType, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        JsonFilterObject jsonFilterObject = new JsonFilterObject();
        if (null == object) {
            return ReturnResult.sucess(object);
        }
        if (!methodParameter.getMethod().isAnnotationPresent(SerializeField.class) &&
                !methodParameter.getMethod().isAnnotationPresent(MultiSerializeField.class) &&
                !methodParameter.getMethod().isAnnotationPresent(MoreSerializeField.class)) {
            return ReturnResult.sucess(object);
        }
        /**
         * 处理类进行过滤处理
         */
        if (methodParameter.getMethod().isAnnotationPresent(SerializeField.class)) {
            /**
             * java.lang.reflect.Method.getAnnotation(Class <T> annotationClass)
             * @return: 如果存在于此元素，则返回该元素注释指定的注释类型，否则返回为null
             * 在处理类中，会对应强转为对应的类型，如SerializeField类
             */
            Object obj = methodParameter.getMethod().getAnnotation(SerializeField.class);
            handleAnnotation(SerializeField.class, obj, jsonFilterObject);
        }
        if (methodParameter.getMethod().isAnnotationPresent(MultiSerializeField.class)) {
            Object obj = methodParameter.getMethod().getAnnotation(MultiSerializeField.class);
            handleAnnotation(MultiSerializeField.class, obj, jsonFilterObject);
        }
        if (methodParameter.getMethod().isAnnotationPresent(MoreSerializeField.class)) {
            MoreSerializeField moreSerializeField = methodParameter.getMethod().getAnnotation(MoreSerializeField.class);
            SerializeField[] serializeFields = moreSerializeField.value();
            if (serializeFields.length > 0) {
                for (int i = 0; i < serializeFields.length; i++) {
                    handleAnnotation(SerializeField.class, serializeFields[i], jsonFilterObject);
                }
            }
        }
        /**
         * 不进行set，返回null，因为未初始化
         */
        jsonFilterObject.setObject(ReturnResult.sucess(object));
        return jsonFilterObject;
    }

    private void handleAnnotation(Class clazz, Object object, JsonFilterObject jsonFilterObject) {
        String[] includes = {};
        String[] excludes = {};
        Class objClass = null;
        if (clazz.equals(SerializeField.class)) {
            SerializeField serializeField = (SerializeField) object;
            /**
             * 获取注解使用处的参数信息，如@SerializeField(clazz = Address.class,includes = {"school", "home", "user"})
             * 获取clazz及includes等信息
             */
            includes = serializeField.includes();
            excludes = serializeField.excludes();
            objClass = serializeField.clazz();
        }
        if (clazz.equals(MultiSerializeField.class)) {
            MultiSerializeField serializeField = (MultiSerializeField) object;
            /**
             * 获取注解使用处的参数信息，如@SerializeField(clazz = Address.class,includes = {"school", "home", "user"})
             * 获取clazz及includes等信息
             */
            includes = serializeField.includes();
            excludes = serializeField.excludes();
            objClass = serializeField.clazz();
        }
        if (includes.length > 0 && excludes.length > 0) {
            throw new IncludesAndExcludesConflictException("Can not use both includes and excludes in the same annotation!");
        } else if (includes.length > 0) {
            jsonFilterObject.getIncludes().put(objClass, new HashSet<String>(Arrays.asList(includes)));
        } else if (excludes.length > 0) {
            jsonFilterObject.getExcludes().put(objClass, new HashSet<String>(Arrays.asList(excludes)));
        }
    }
}
