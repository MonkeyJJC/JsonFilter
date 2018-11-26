package com.monkey.jsonfilter.controller.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: JJC
 * @createTime: 2018/11/23
 */
@Data
@AllArgsConstructor
public class User {

    private Long id;

    private String name;

    private String password;

    private List<Address> addresses;

    public User(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
}
