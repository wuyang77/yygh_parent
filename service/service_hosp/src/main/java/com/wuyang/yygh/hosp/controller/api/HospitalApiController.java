package com.wuyang.yygh.hosp.controller.api;

import com.wuyang.yygh.common.result.R;
import com.wuyang.yygh.hosp.service.DepartmentService;
import com.wuyang.yygh.hosp.service.HospitalService;
import com.wuyang.yygh.hosp.service.ScheduleService;
import com.wuyang.yygh.model.hosp.Hospital;
import com.wuyang.yygh.model.hosp.Schedule;
import com.wuyang.yygh.vo.hosp.DepartmentVo;
import com.wuyang.yygh.vo.hosp.HospitalQueryVo;
import com.wuyang.yygh.vo.hosp.ScheduleOrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "本地医院系统接口")
@RestController
@RequestMapping("/api/hosp/hospital")
public class HospitalApiController {
    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ScheduleService scheduleService;

    @ApiOperation(value = "订单页面触发的，根据排班id获取预约下单数据")
    @GetMapping("inner/getScheduleOrderVo/{scheduleId}")
    public ScheduleOrderVo getScheduleOrderVo(
            @ApiParam(name = "scheduleId", value = "排班id", required = true)
            @PathVariable("scheduleId") String scheduleId) {
        return scheduleService.getScheduleOrderVo(scheduleId);
    }

    @ApiOperation(value = "订单页面触发的，根据排排班id获取排班")
    @GetMapping("/getSchedule/{id}")
    public R getScheduleList(@PathVariable(value = "id") String scheduleId) {
        Schedule schedule = scheduleService.findAllByScheduleId(scheduleId);
        return R.ok().data("schedule",schedule);
    }

    @ApiOperation(value = "获取可预约排班数据")
    @GetMapping("/auth/getBookingScheduleRule/{page}/{limit}/{hoscode}/{depcode}")
    public R getBookingSchedule(
            @PathVariable Integer page,
            @PathVariable Integer limit,
            @PathVariable String hoscode,
            @PathVariable String depcode) {

        Map<String, Object> map = scheduleService.getBookingScheduleRule(page, limit, hoscode, depcode);
        return R.ok().data(map);
    }

    @ApiOperation(value = "获取排班数据")
    @GetMapping("auth/findScheduleList/{hoscode}/{depcode}/{workDate}")
    public R findScheduleList(
            @PathVariable String hoscode,
            @PathVariable String depcode,
            @PathVariable String workDate) {
        List<Schedule> scheduleList = scheduleService.getDetailSchedule(hoscode, depcode, workDate);
        return R.ok().data("scheduleList",scheduleList);
    }

    @ApiOperation(value = "根据医院编号，查询医院信息")
    @GetMapping("/info/{hoscode}")
    public R info(@PathVariable(value = "hoscode")String hoscode){
        Hospital byHoscode = hospitalService.findHospitalByHoscode(hoscode);
        return R.ok().data("hospital",byHoscode);
    }
    @ApiOperation(value = "根据医院编号，查询当前医院底下所有的科室信息,大科室嵌套小科室")
    @GetMapping("/department/list/{hoscode}")
    public R getDepartmentListByHoscode(@PathVariable(value = "hoscode")String hoscode){
        List<DepartmentVo> deptTree = departmentService.findDeptTree(hoscode);
        return R.ok().data("list",deptTree);
    }
    @ApiOperation(value = "获取带查询条件的首页医院列表")
    @GetMapping("{page}/{limit}")
    public R index(@PathVariable(value = "page") Integer page,
                   @PathVariable(value = "limit") Integer limit,HospitalQueryVo hospitalQueryVo){
        Page<Hospital> hospitals = hospitalService.selectPage(page, limit, hospitalQueryVo);
        return R.ok().data("pages",hospitals);
    }
    @ApiOperation(value = "根据医院名称进行模糊查询，查询所有的符合条件的医院信息")
    @GetMapping("findByNameLike/{name}")
    public R findByNameLike(@ApiParam(name = "hosname", value = "医院名称", required = true)
                           @PathVariable(value = "name") String name) {

        List<Hospital> list = hospitalService.findByNameLike(name);
        return R.ok().data("list",list);
    }
}