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
    /**
     * 用户手机号或者微信扫码登录，手机号和微信号绑定
     * @param loginVo
     * @return
     */
    Map<String, Object> login(LoginVo loginVo);

    /**
     * 根据OpenId查询用户信息
     * @param openid
     * @return
     */
    UserInfo selectUserInfoByOpenId(String openid);

    /**
     * 更具用户id和用户授权对象更新用户信息
     * @param userId
     * @param userAuthVo
     */
    void userAuth(Long userId, UserAuthVo userAuthVo);

    /**
     * 根据用户id获取用户信息
     * @param userId
     * @return
     */
    UserInfo selectById(Long userId);

    /**
     * 根据page和limit查询用户列表<--条件查询带分页-->
     * @param page
     * @param limit
     * @param userInfoQueryVo
     * @return
     */
    Page<UserInfo> selectUserInfoPage(Integer page, Integer limit, UserInfoQueryVo userInfoQueryVo);

    /**
     * 根据用户的id查询用户，如果状态为0锁定或1正常，则会修改状态
     * @param userId
     * @param status
     */
    void lock(Long userId, Integer status);

    /**
     * 根据用户id展示用户详情
     * @param userId
     * @return
     */
    Map<String, Object> show(Long userId);

    /**
     * 认证状态（0：未认证 1：认证中 2：认证成功 -1：认证失败）
     * 如果认证状态为2通过或者-1不通过，则更新认证状态，前端点击通过或者不通过按钮更新状态
     * @param userId
     * @param authStatus
     */
    void approval(Long userId, Integer authStatus);
}
