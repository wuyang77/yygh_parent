package com.wuyang.yygh.hosp.controller.api;


import com.wuyang.yygh.common.exception.YyghException;
import com.wuyang.yygh.hosp.service.DepartmentService;
import com.wuyang.yygh.hosp.service.HospitalService;
import com.wuyang.yygh.hosp.service.HospitalSetService;
import com.wuyang.yygh.hosp.service.ScheduleService;
import com.wuyang.yygh.hosp.util.HttpRequestHelper;
import com.wuyang.yygh.hosp.util.MD5;
import com.wuyang.yygh.model.hosp.Hospital;
import com.wuyang.yygh.model.hosp.Schedule;
import com.wuyang.yygh.result.Result;
import com.wuyang.yygh.vo.hosp.ScheduleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RestController
@RequestMapping("/api/hosp")
@Api(tags = "第三方医院系统API接口")
public class ApiController {
    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private HospitalSetService hospitalSetService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ScheduleService scheduleService;
    @ApiOperation(value = "上传医院")
    @PostMapping("/saveHospital")
    public Result saveHospital(HttpServletRequest request) {
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //校验
        String sign = (String) stringObjectMap.get("sign");
        String platformSignKey = hospitalSetService.getSignKey((String)stringObjectMap.get("hoscode"));
        if(!StringUtils.isEmpty(sign) && !StringUtils.isEmpty(platformSignKey) && MD5.encrypt(platformSignKey).equals(sign)){
            hospitalService.save(stringObjectMap);
        }else {
            throw new YyghException(2001,"校验失败");
        }

        //传输过程中“+”转换为了“ ”，因此我们要转换回来
        String logoData = (String)stringObjectMap.get("logoData");
        logoData = logoData.replaceAll(" ","+");
        stringObjectMap.put("logoData",logoData);
        hospitalService.save(stringObjectMap);
        return Result.ok(); //TODO 有待完善
    }

    @ApiOperation(value = "查询医院")
    @PostMapping("/hospital/show")
    public Result showHospital(HttpServletRequest request){
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //TODO 校验
        String hoscode = (String) stringObjectMap.get("hoscode");
        Hospital hospital = hospitalService.findHospitalByHoscode(hoscode);
        return Result.ok(hospital);
    }

    @ApiOperation(value = "上传医院科室信息")
    @PostMapping("/saveDepartment")
    public Result saveDepartment(HttpServletRequest request){
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //TODO 校验
        departmentService.saveDepartment(stringObjectMap);
        return Result.ok();
    }

    @ApiOperation(value = "获取医院科室分页列表")
    @PostMapping("/department/list")
    public Result getDepartmentPage(HttpServletRequest request){
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //TODO 校验
        Page page = departmentService.findDeparmentPage(stringObjectMap);
        return Result.ok(page);
    }

    @ApiOperation(value = "删除医院科室")
    @PostMapping("/department/remove")
    public Result removeDepartment(HttpServletRequest request){
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(request.getParameterMap());
        String hoscode = (String) stringObjectMap.get("hoscode");
        String depcode = (String) stringObjectMap.get("depcode");
        departmentService.removeDepartment(hoscode,depcode);
        return Result.ok();
    }

    @ApiOperation(value = "上传排班")
    @PostMapping("/saveSchedule")
    public Result saveSchedule(HttpServletRequest request){
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(request.getParameterMap());
        scheduleService.saveSchedule(stringObjectMap);
        return Result.ok();
    }

    @ApiOperation(value = "获取排班分页列表")
    @PostMapping("schedule/list")
    public Result schedule(HttpServletRequest request){
        Map<String,Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //必须参数校验 略
        String hoscode = (String)paramMap.get("hoscode");
        //非必填
        String depcode = (String)paramMap.get("depcode");
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 10 : Integer.parseInt((String)paramMap.get("limit"));

        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setHoscode(hoscode);
        scheduleQueryVo.setDepcode(depcode);
        Page<Schedule> pageModel = scheduleService.selectPage(page,limit,scheduleQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "删除排班")
    @PostMapping("schedule/remove")
    public Result removeScheduleByRequest(HttpServletRequest request){

        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        String hoscode = (String) paramMap.get("hoscode");
        String hosScheduleId = (String) paramMap.get("hosScheduleId");
        scheduleService.removeScheduleByRequest(paramMap);
        return Result.ok();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
