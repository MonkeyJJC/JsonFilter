package com.monkey.jsonfilter.controller.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

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
}
