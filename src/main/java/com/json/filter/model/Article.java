package com.json.filter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @description:
 * @author: JJC
 * @createTime: 2018/11/20
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    public String id;
    public String title;
    public String content;
}
