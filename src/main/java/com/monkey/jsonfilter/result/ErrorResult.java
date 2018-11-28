package com.monkey.jsonfilter.result;

import lombok.Data;

/**
 * @description:
 * @author: JJC
 * @createTime: 2018/11/28
 */
@Data
public class ErrorResult {

    public int code;
    public String message;

    public ErrorResult() {
    }

    public ErrorResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

}