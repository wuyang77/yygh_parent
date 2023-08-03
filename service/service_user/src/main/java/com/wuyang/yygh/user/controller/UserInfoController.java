package com.wuyang.yygh.user.controller;


import com.wuyang.yygh.common.result.R;
import com.wuyang.yygh.model.user.UserInfo;
import com.wuyang.yygh.user.service.UserInfoService;
import com.wuyang.yygh.user.util.AuthContextHolder;
import com.wuyang.yygh.vo.user.LoginVo;
import com.wuyang.yygh.vo.user.UserAuthVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author wuyang
 * @since 2023-03-28
 */
@Api(tags = "用户登录接口")
@RestController
@RequestMapping("/api/userinfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation(value = "会员登录")
    @PostMapping("/login")
    public R login(@RequestBody LoginVo loginVo) {
        Map<String, Object> map = userInfoService.login(loginVo);
        return R.ok().data(map);
    }

    //options:预请求
    @ApiOperation(value = "获取用户信息")
    @GetMapping("/auth/getUserInfo")
    public R getUserInfo(HttpServletRequest request) {
        Long userId = AuthContextHolder.getUserId(request);
        UserInfo userInfo = userInfoService.selectById(userId);
        return R.ok().data("userInfo",userInfo);
    }
    @ApiOperation(value = "用户认证接口")
    @PostMapping("/auth/userAuth")
    public R userAuth(@RequestBody UserAuthVo userAuthVo, HttpServletRequest request) {
        //传递两个参数，第一个参数用户id，第二个参数认证数据vo对象
        userInfoService.userAuth(AuthContextHolder.getUserId(request),userAuthVo);
        return R.ok();
    }
}

