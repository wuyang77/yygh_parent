package com.wuyang.yygh.hosp.controller;

import com.wuyang.yygh.common.result.R;
import com.wuyang.yygh.hosp.service.ScheduleService;
import com.wuyang.yygh.model.hosp.Schedule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
@Api(tags = "科室排班接口")
@RestController
@RequestMapping("/admin/hosp/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    //根据医院编号和科室编号 ，查询排班规则数据
    @ApiOperation(value ="查询排班规则")
    @GetMapping("/getScheduleRule/{page}/{limit}/{hoscode}/{depcode}")
    public R getScheduleRule(@PathVariable(value = "page")Integer page,
                             @PathVariable(value = "limit")Integer limit,
                             @PathVariable(value = "hoscode") String hoscode,
                             @PathVariable(value = "depcode") String depcode){
        Map<String,Object> map = scheduleService.getRuleSchedule(page,limit,hoscode,depcode);
        return R.ok().data(map);
    }

    //根据医院编号 、科室编号和工作日期，查询排班详细信息
    @ApiOperation(value = "查询排班详细信息")
    @GetMapping("/getScheduleDetail/{hoscode}/{depcode}/{workDate}")
    public R getScheduleDetail( @PathVariable(value = "hoscode") String hoscode,
                                @PathVariable(value = "depcode") String depcode,
                                @PathVariable(value = "workDate") String workDate) {
        List<Schedule> list = scheduleService.getDetailSchedule(hoscode,depcode,workDate);
        return R.ok().data("list",list);
    }
}