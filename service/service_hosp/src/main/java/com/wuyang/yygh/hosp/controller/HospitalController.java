package com.wuyang.yygh.hosp.controller;

import com.wuyang.yygh.common.result.R;
import com.wuyang.yygh.hosp.service.HospitalService;
import com.wuyang.yygh.model.hosp.Hospital;
import com.wuyang.yygh.vo.hosp.HospitalQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = "医院接口")
@RestController
@RequestMapping("/admin/hosp/hospital")
//@CrossOrigin
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    //根据医院id获取医院的详情信息
    @ApiOperation(value = "获取医院详情")
    @GetMapping("/detail/{id}")
    public R show(@ApiParam(name = "id", value = "医院id", required = true)
                  @PathVariable String id) {
        Map<String,Object> map = hospitalService.getHospitalDetailById(id);
        return R.ok().data("hospital",map);
    }

    //更新医院上线和下线状态
    @ApiOperation(value = "更新上线状态")
    @PutMapping("/updateStatus/{id}/{status}")
    public R lock(@ApiParam(name = "id", value = "医院id", required = true)
                  @PathVariable("id") String id,
                  @ApiParam(name = "status", value = "状态（0：未上线 1：已上线）", required = true)
                  @PathVariable("status") Integer status){
        hospitalService.updateStatus(id, status);
        return R.ok();
    }
    @ApiOperation(value = "获取分页列表")
    @GetMapping("/{page}/{limit}")
    public R getHospitalPage(@PathVariable Integer page,
                             @PathVariable Integer limit,HospitalQueryVo hospitalQueryVo){
        Page<Hospital> pageObj = hospitalService.selectPage(page,limit,hospitalQueryVo);

        return R.ok().data("pages",pageObj);
    }
}