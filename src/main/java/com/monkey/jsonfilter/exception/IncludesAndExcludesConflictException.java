package com.monkey.jsonfilter.exception;

/**
 * @description:
 * @author: JJC
 * @createTime: 2018/11/23
 */
public class IncludesAndExcludesConflictException extends RuntimeException{

    public IncludesAndExcludesConflictException(String msg) {
        super(msg);
    }

}
