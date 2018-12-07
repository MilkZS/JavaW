package com.huada.demo.http;

import lombok.Data;

/**
 * @description: HttpClient 返回值实体
 **/
@Data
public class HttpResult {
    //响应状态码
    private int code;
    //响应body体
    private String body;

    public HttpResult(int code, String body) {
        this.code = code;
        this.body = body;
    }
}