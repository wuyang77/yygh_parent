package com.wuyang.yygh.orders.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "流程控制接口")
@RestController
@RequestMapping("/test")
public class SentinelController {

    @GetMapping
    public String test1() {
        return "hello";
    }
}