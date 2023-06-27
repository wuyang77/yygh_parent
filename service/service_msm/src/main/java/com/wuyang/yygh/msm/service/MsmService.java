package com.wuyang.yygh.msm.service;

import com.wuyang.yygh.vo.msm.MsmVo;

public interface MsmService {
    boolean sendCode(String phone);

    void send(MsmVo msmVo);
}
