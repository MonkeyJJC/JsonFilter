package com.monkey.jsonfilter.controller;

import com.monkey.jsonfilter.annotation.SerializeField;
import com.monkey.jsonfilter.controller.bean.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: JJC
 * @createTime: 2018/11/23
 */
@RestController
public class Demo {

    @GetMapping("user")
    @SerializeField(clazz = User.class, includes = {"name", "id"})
    public User user() {
        User user = new User(1L, "jjc", "123456");
        return user;
    }
}
