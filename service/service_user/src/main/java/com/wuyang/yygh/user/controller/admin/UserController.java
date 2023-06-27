package com.wuyang.yygh.user.controller.admin;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuyang.yygh.common.result.R;
import com.wuyang.yygh.model.user.UserInfo;
import com.wuyang.yygh.user.service.UserInfoService;
import com.wuyang.yygh.vo.user.UserInfoQueryVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/userinfo")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    //用户列表（条件查询带分页）
    @GetMapping("/{page}/{limit}")
    private R getUserInfoPage(@PathVariable("page") Integer page,
                              @PathVariable("limit") Integer limit,
                              UserInfoQueryVo userInfoQueryVo){
        //mybatis plus
        Page<UserInfo> pageModel = userInfoService.selectUserInfoPage(page,limit,userInfoQueryVo);
        return R.ok().data("pageModel",pageModel);
    }

    @ApiOperation(value = "锁定")
    @GetMapping("/lock/{userId}/{status}")
    public R lock(
            @PathVariable("userId") Long userId,
            @PathVariable("status") Integer status){
        userInfoService.lock(userId, status);
        return R.ok();
    }

    //用户详情
    @GetMapping("/show/{userId}")
    public R show(@PathVariable Long userId) {
        Map<String,Object> map = userInfoService.show(userId);
        return R.ok().data(map);
    }
    //认证审批
    @GetMapping("/approval/{userId}/{authStatus}")
    public R approval(@PathVariable Long userId,@PathVariable Integer authStatus) {
        userInfoService.approval(userId,authStatus);
        return R.ok();
    }
}
