package com.huada.demo.service.impl;

import com.huada.demo.http.HttpAPIService;
import com.huada.demo.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 用来发送数据到目标服务器
 **/
@Service
public class ApiServiceImpl implements ApiService {

    @Autowired
    HttpAPIService httpAPIService;

    @Override
    public String doSomething(String a) {
        //TODO  DOSomething
        a="Hello World!";
        String url = "http://127.0.0.1:8080/test/receive";
        Map<String,Object> m = new HashMap<>();
        m.put("test","加密后的数据："+a);
        try {
            httpAPIService.doPost(url,m);
        } catch (Exception e) {
            //TODO 自定义异常进行异常拦截
            e.printStackTrace();
        }
        return a;
    }
}
