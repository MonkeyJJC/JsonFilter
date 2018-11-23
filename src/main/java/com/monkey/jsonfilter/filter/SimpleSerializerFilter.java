package com.monkey.jsonfilter.filter;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @description: 对SimplePropertyPreFilter进行一层封装
 * github：https://github.com/alibaba/fastjson/wiki/%E4%BD%BF%E7%94%A8SimplePropertyPreFilter%E8%BF%87%E6%BB%A4%E5%B1%9E%E6%80%A7
 * @author: JJC
 * @createTime: 2018/11/23
 */
public class SimpleSerializerFilter extends SimplePropertyPreFilter {
    private Map<Class, HashSet<String>> includes;
    private  Map<Class, HashSet<String>> excludes;

    public SimpleSerializerFilter(Map<Class, HashSet<String>> includes, Map<Class, HashSet<String>> excludes) {
        this.includes = includes;
        this.excludes = excludes;
    }

    @Override
    public boolean apply(JSONSerializer serializer, Object source, String name) {
        if(!isEmpty(includes)){
            for (Map.Entry<Class, HashSet<String>> include : includes.entrySet()){
                Class objClass = include.getKey();
                Set<String> includeProp = include.getValue();
                if(objClass.isAssignableFrom(source.getClass())){
                    return includeProp.contains(name);
                }else {
                    continue;
                }
            }
        }
        if(!isEmpty(excludes)){
            for (Map.Entry<Class, HashSet<String>> exclude : excludes.entrySet()){
                Class objClass = exclude.getKey();
                Set<String> includeProp = exclude.getValue();
                if(objClass.isAssignableFrom(source.getClass())){
                    return !includeProp.contains(name);
                }else {
                    continue;
                }
            }
        }
        return true;
    }

    public boolean isEmpty(Map map){
        return map == null || map.size() < 1;
    }
}
