package com.monkey.jsonfilter.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.monkey.jsonfilter.bean.JsonFilterObject;
import com.monkey.jsonfilter.filter.SimpleSerializerFilter;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;

import static com.alibaba.fastjson.util.IOUtils.UTF8;

/**
 * @description: 自定义消息转换器
 * FastJson是阿里巴巴开源的高性能JSON转换工具，使用Spring MVC需要进行JSON转换时，通常会使用FastJson提供的FastJsonHttpMessageConverter
 * @author: JJC
 * @createTime: 2018/11/23
 */
public class JsonFilterHttpMessageConverter extends FastJsonHttpMessageConverter {

    private Charset charset;
    private SerializerFeature[] features;

    public JsonFilterHttpMessageConverter() {
        super();
        setSupportedMediaTypes(Arrays.asList(
                new MediaType("application", "json", UTF8),
                new MediaType("application", "*+json", UTF8),
                new MediaType("application", "jsonp", UTF8),
                new MediaType("application", "*+jsonp", UTF8)));
        setCharset(UTF8);
        setFeatures(SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue);
    }

    /**
     *
     * @param obj- the object to write to the output message
     * @param outputMessage- the HTTP output message to write to
     * @throws IOException- in case of I/O errors
     * @throws HttpMessageNotWritableException- in case of conversion errors
     * 官方地址：https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/http/converter/AbstractGenericHttpMessageConverter.html#writeInternal-T-org.springframework.http.HttpOutputMessage-
     */
    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        if(obj instanceof JsonFilterObject){
            JsonFilterObject jsonFilterObject = (JsonFilterObject) obj;
            OutputStream out = outputMessage.getBody();
            SimpleSerializerFilter simpleSerializerFilter = new SimpleSerializerFilter(jsonFilterObject.getIncludes(), jsonFilterObject.getExcludes());
            /**
             * JSON序列化接口toJSONString
             * String toJSONString(Object, SerializeFilter, SerializerFeature...)
             */
            String text = JSON.toJSONString(jsonFilterObject.getObject(), simpleSerializerFilter, features);
            byte[] bytes = text.getBytes(this.charset);
            out.write(bytes);
        }else {
            /**
             * 未声明@SerializeField注解
             */
            OutputStream out = outputMessage.getBody();
            String text = JSON.toJSONString(obj, this.features);
            byte[] bytes = text.getBytes(this.charset);
            out.write(bytes);
        }
    }

    @Override
    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    @Override
    public void setFeatures(SerializerFeature... features) {
        this.features = features;
    }
}
