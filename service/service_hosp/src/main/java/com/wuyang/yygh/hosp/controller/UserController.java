package com.wuyang.yygh.hosp.controller;

import com.wuyang.yygh.common.result.R;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
//@CrossOrigin//允许跨域，注解的方式
@Api(tags = "用户接口")
@RestController
@RequestMapping("/yygh/user")
@Slf4j
public class UserController {
    @RequestMapping("/login")
    public R login(){

        return  R.ok().data("token","admin-token");
    }

    @GetMapping ("/info")
    public R info(String token){
        log.info("token为："+token);
        return  R.ok()
                .data("roles","[admin]")
                .data("introduction","I am a super administrator")
                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif")
                .data("name","Super Admin");
    }
}

