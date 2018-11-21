package com.json.filter.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;

/**
 * @description: 使用JsonFieldFilter时，不要加@ResponseBody，否则会被RequestResponseBodyMethodProcessor拦截掉，
 * 想了解具体情况请参考：RequestMappingHandlerAdapter
 * @author: JJC
 * @createTime: 2018/11/20
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonFieldFilter {

    Class<?> type() default Object.class;

    /**
     * 哪些字段可以显示，即包含哪些字段
     * @return
     */
    String[] include() default {};

    /**
     * 哪些字段不可以显示，即不包含哪些字段
     * @return
     */
    String[] exclude() default {};
}
