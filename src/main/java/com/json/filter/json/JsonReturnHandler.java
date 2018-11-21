package com.json.filter.json;

/**
 * @description: 实现spring MVC的HandlerMethodReturnHandler
 * @author: JJC
 * @createTime: 2018/11/21
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;


/**
 * HandlerMethodReturnValueHandler 接口 Spring MVC 用于处理请求返回值:
 * (1)supportsReturnType 用来判断 处理类 是否支持当前请求, 返回boolean类型
 * (2)handleReturnValue 就是具体返回逻辑的实现
 * 注：
 * (1)平时使用 @ResponseBody 就是交给 RequestResponseBodyMethodProcessor 这个类处理的
 * (2)返回 ModelAndView（视图解析器, 对象中包含了一个model属性和一个view属性） 的时候，是由 ModelAndViewMethodReturnValueHandler 类处理的，
 * 视图解析器将model中的每个元素都通过request.setAttribute(name, value);添加request请求域中。这样就可以在JSP页面中通过EL表达式来获取对应的值
 */
public class JsonReturnHandler extends HandlerMethodReturnValueHandlerComposite {

    private static Logger logger = LoggerFactory.getLogger(JsonReturnHandler.class);

    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        return methodParameter.hasMethodAnnotation(JsonFieldFilter.class);
    }

    @Override
    public void handleReturnValue(Object returnObject, MethodParameter parameter,
                                  ModelAndViewContainer container, NativeWebRequest request) throws Exception {
        /**
         * 设置最终处理类， 处理完不再去找下一个类进行处理
         */
        container.setRequestHandled(true);
        /**
         * Json过滤处理类
         */
        JsonFilterSerializer serializer = new JsonFilterSerializer();

        if (logger.isDebugEnabled()) {
            logger.debug("Return Value: " + returnObject.toString());
            logger.debug("Return Type:" + parameter);
        }

        /**
         * 对于使用JsonFieldFilter注解的情况，进行过滤
         */
        if (parameter.hasMethodAnnotation(JsonFieldFilter.class)) {
            JsonFieldFilter jsonFieldFilter = parameter.getMethodAnnotation(JsonFieldFilter.class);
            /**
             * 执行过滤操作
             */
            serializer.filter(null == jsonFieldFilter.type() ? returnObject.getClass() : jsonFieldFilter.type(), jsonFieldFilter.include(), jsonFieldFilter.exclude());
            HttpServletResponse response = request.getNativeResponse(HttpServletResponse.class);
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(serializer.toJson(returnObject));
        }

    }

}
