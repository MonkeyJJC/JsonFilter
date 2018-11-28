package com.monkey.jsonfilter.result;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 统一返回结果
 * @author: JJC
 * @createTime: 2018/11/28
 */
@Data
@NoArgsConstructor
public class ReturnResult {

    private Integer code;

    private Object data;

    private String msg;

    public static ReturnResult sucess(Object data) {
        ReturnResult returnResult = new ReturnResult();
        returnResult.setCode(200);
        returnResult.setData(data);
        returnResult.setMsg("success");
        return returnResult;
    }
}
