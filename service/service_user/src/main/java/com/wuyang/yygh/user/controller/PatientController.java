package com.wuyang.yygh.user.controller;


import com.wuyang.yygh.cmn.client.DictFeignClient;
import com.wuyang.yygh.common.result.R;
import com.wuyang.yygh.model.user.Patient;
import com.wuyang.yygh.user.service.PatientService;
import com.wuyang.yygh.user.util.AuthContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 就诊人表 前端控制器
 * </p>
 *
 * @author wuyang
 * @since 2023-04-05
 */
//就诊人管理接口
@RestController
@RequestMapping("/api/userinfo/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;
    //添加就诊人
    @PostMapping("/auth/save")
    public R savePatient(@RequestBody Patient patient, HttpServletRequest request){
        Long userId = AuthContextHolder.getUserId(request);
        patient.setUserId(userId);
        patientService.save(patient);
        return R.ok();
    }

    //删除就诊人
    @DeleteMapping("/auth/remove/{id}")
    public R removePatient(@PathVariable("id")Integer id) {
        patientService.removeById(id);
        return R.ok();
    }

    //获取所有就诊人列表
    @GetMapping("/auth/findAll")
    public R findAll(HttpServletRequest request) {
        //获取当前登录用户id
        Long userId = AuthContextHolder.getUserId(request);
        List<Patient> list = patientService.findAllPatientById(userId);
        return R.ok().data("list",list);
    }

    //根据id获取就诊人信息
    @GetMapping("/auth/get/{id}")
    public R getPatient(@PathVariable(value = "id") Long id) {
        Patient patient = patientService.getPatientInfo(String.valueOf(id));
        return R.ok().data("patient",patient);
    }
    //修改就诊人
    @PostMapping("/auth/update")
    public R updatePatient(@RequestBody Patient patient) {
        patientService.updateById(patient);
        return R.ok();
    }


}

