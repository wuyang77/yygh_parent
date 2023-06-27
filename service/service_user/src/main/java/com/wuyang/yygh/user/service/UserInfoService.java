package com.wuyang.yygh.user.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyang.yygh.model.user.UserInfo;
import com.wuyang.yygh.vo.user.LoginVo;
import com.wuyang.yygh.vo.user.UserAuthVo;
import com.wuyang.yygh.vo.user.UserInfoQueryVo;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author wuyang
 * @since 2023-03-28
 */
public interface UserInfoService extends IService<UserInfo> {

    Map<String, Object> login(LoginVo loginVo);

    UserInfo selectByOpenId(String openid);

    void userAuth(Long userId, UserAuthVo userAuthVo);

    UserInfo selectById(Long userId);

    Page<UserInfo> selectUserInfoPage(Integer page, Integer limit, UserInfoQueryVo userInfoQueryVo);

    void lock(Long userId, Integer status);

    Map<String, Object> show(Long userId);

    void approval(Long userId, Integer authStatus);
}
