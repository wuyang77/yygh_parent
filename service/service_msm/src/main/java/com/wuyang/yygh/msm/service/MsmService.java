package com.wuyang.yygh.msm.service;

import com.wuyang.yygh.vo.msm.MsmVo;

public interface MsmService {
    /**
     * 发送验证码
     * @param phone
     * @return
     */
    boolean sendCode(String phone);

    /**
     * 发送短信通知
     * @param msmVo
     */
    void sendNotice(MsmVo msmVo);
}
