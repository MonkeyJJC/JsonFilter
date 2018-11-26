package com.monkey.jsonfilter.controller.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description:
 * @author: JJC
 * @createTime: 2018/11/26
 */
@Data
@AllArgsConstructor
public class Address {
    private String home;

    private String school;

    private User user;
}
