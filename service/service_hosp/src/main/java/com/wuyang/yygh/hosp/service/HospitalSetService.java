package com.wuyang.yygh.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyang.yygh.model.hosp.HospitalSet;

/**
 * <p>
 * 医院设置表 服务类
 * </p>
 *
 * @author wuyang
 * @since 2023-02-26
 */
public interface HospitalSetService extends IService<HospitalSet> {
    /**
     * 根据医院编号查询签名秘钥
     * @param hoscode
     * @return
     */
    String getSignKey(String hoscode);
}
