package com.huada.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.huada.demo.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 接受来自https://127.0.0.1:9900/api/demo 下的消息
 **/
@RestController
@RequestMapping("api")
public class ApiController {
    @Autowired
    ApiService apiServer;

    @PostMapping("demo")
    public String api(@RequestBody JSONObject jsonObject){
        String a = jsonObject.getString("a");
        a = apiServer.doSomething(a);

        return "SUCCESS:"+a;
    }

    @PostMapping("demo1")
    public String api1(@RequestBody JSONObject jsonObject){
        String a = jsonObject.getString("a");
        a = apiServer.doSomething(a);

        return "SUCCESS:"+a;
    }
}
