package com.wuyang.yygh.user.client;

import com.wuyang.yygh.common.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-user")
public interface PatientFeignClient {
    //获取就诊人
    @GetMapping("/api/userinfo/patient/auth/get/{id}")
    R getPatient(@PathVariable("id") Long id);
}