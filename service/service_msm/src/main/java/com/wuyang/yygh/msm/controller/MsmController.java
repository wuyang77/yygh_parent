package com.wuyang.yygh.msm.controller;

import com.wuyang.yygh.common.result.R;
import com.wuyang.yygh.msm.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/msm")
public class MsmController {
    @Autowired
    private MsmService msmService;

    @GetMapping(value = "/send/{phone}")//发送验证码需要知道手机号是多少
    public R sendCode(@PathVariable("phone")String phone) {
        boolean flag = msmService.sendCode(phone);
        if (flag) {
            return R.ok();
        } else {
            return R.error().message("验证码发送失败");
        }
    }
}