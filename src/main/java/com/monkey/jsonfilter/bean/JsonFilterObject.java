package com.monkey.jsonfilter.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @description: 存储待处理类的对象及待过滤字段信息
 * @author: JJC
 * @createTime: 2018/11/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonFilterObject {

    private Object object;

    private Map<Class, HashSet<String>> includes = new HashMap<>();

    private Map<Class, HashSet<String>> excludes = new HashMap<>();

}
