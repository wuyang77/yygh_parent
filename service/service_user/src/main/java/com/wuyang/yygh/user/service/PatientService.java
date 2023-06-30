package com.wuyang.yygh.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyang.yygh.model.user.Patient;

import java.util.List;

/**
 * <p>
 * 就诊人表 服务类
 * </p>
 *
 * @author wuyang
 * @since 2023-04-05
 */
public interface PatientService extends IService<Patient> {
    /**
     * 根据id获取就该诊人信息
     * @param id
     * @return
     */
    Patient getPatientInfo(String id);

    /**
     * 根据当前用户id查询当前所有的就诊记录信息
     * @param userId
     * @return
     */
    List<Patient> findAllPatientById(Long userId);
}
