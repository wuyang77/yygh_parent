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

    Patient getPatientInfo(String id);

    List<Patient> findAllPatientById(Long userId);
}
