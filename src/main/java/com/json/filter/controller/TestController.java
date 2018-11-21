package com.json.filter.controller;

import com.json.filter.json.JsonFieldFilter;
import com.json.filter.model.Article;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description:
 * @author: JJC
 * @createTime: 2018/11/20
 */

/**
 * pom文件导入spring-boot-starter-web，即@RestController等相关注解的依赖
 */
@Controller
@RequestMapping("test")
public class TestController {

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello() {
        return "Hello World";
    }

    @RequestMapping(value = "jsonFilter", method = RequestMethod.GET)
    @JsonFieldFilter(type = Article.class, include = "content")
    public String get() {
        return new Article("1", "Hello World", "The first step").toString();
    }
}
