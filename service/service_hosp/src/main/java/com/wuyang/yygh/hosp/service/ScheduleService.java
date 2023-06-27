package com.wuyang.yygh.hosp.service;

import com.wuyang.yygh.model.hosp.Schedule;
import com.wuyang.yygh.vo.hosp.ScheduleOrderVo;
import com.wuyang.yygh.vo.hosp.ScheduleQueryVo;
import com.wuyang.yygh.vo.order.OrderMqVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ScheduleService {
    void saveSchedule(Map<String, Object> stringObjectMap);

    Page<Schedule> selectPage(int page, int limit, ScheduleQueryVo scheduleQueryVo);


    void removeScheduleByHospitalIdAndScheId(Map<String, Object> paramMap);

    Map<String, Object> getRuleSchedule(Integer page, Integer limit, String hoscode, String depcode);

    List<Schedule> getDetailSchedule(String hoscode,String depcode,String workDate);

    Map<String, Object> getBookingScheduleRule(Integer page, Integer limit, String hoscode, String depcode);

    Schedule findAllByScheduleId(String scheduleId);

    ScheduleOrderVo getScheduleOrderVo(String scheduleId);

    void update(OrderMqVo orderMqVo);
}