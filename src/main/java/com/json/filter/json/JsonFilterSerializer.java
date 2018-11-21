package com.json.filter.json;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * @description: 自定义过滤器，
 * Jackson框架的高阶应用 ：https://www.ibm.com/developerworks/cn/java/jackson-advanced-application/index.html
 * @author: JJC
 * @createTime: 2018/11/20
 */
public class JsonFilterSerializer {

    /**
     * 包含的标识
     */
    private static final String DYNC_INCLUDE = "DYNC_INCLUDE";

    /**
     * 过滤的标识
     */
    private static final String DYNC_EXCLUDE = "DYNC_EXCLUDE";

    /**
     * com.fasterxml.jackson编程式过滤字段
     */
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * 设置@JsonFilter 类或接口
     */
    @JsonFilter(DYNC_INCLUDE)
    interface DynamicInclude {

    }

    @JsonFilter(DYNC_EXCLUDE)
    interface DynamicExclude {

    }

    public void filter(Class<?> clazz, String[] include, String[] exclude) {
        if (null == clazz) {
            return;
        }

        if (null != include && include.length > 0) {
            mapper.setFilterProvider(new SimpleFilterProvider()
            .addFilter(DYNC_INCLUDE, SimpleBeanPropertyFilter.filterOutAllExcept(include)));

            /**
             * 设置addMixIn
             */
            mapper.addMixIn(clazz, DynamicInclude.class);
        } if (null != exclude && exclude.length > 0) {
            mapper.setFilterProvider(new SimpleFilterProvider()
            .addFilter(DYNC_EXCLUDE, SimpleBeanPropertyFilter.serializeAllExcept(exclude)));
            mapper.addMixIn(clazz, DynamicExclude.class);
        }
    }

    public String toJson(Object object) throws JsonProcessingException {
        String json = mapper.writeValueAsString(object);
        return "{\"data\":" + json + "}";
    }
}
