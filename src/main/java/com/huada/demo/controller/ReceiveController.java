package com.huada.demo.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 **/
@RestController
@RequestMapping("/api1")
public class ReceiveController {

    @RequestMapping("demo")
    public String receive(@RequestBody String t){
        System.out.println("接收到一次消息"+t);
        return "接受一次消息"+t;
    }
}
