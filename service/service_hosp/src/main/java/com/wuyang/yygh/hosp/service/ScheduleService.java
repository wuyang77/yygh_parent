package com.wuyang.yygh.hosp.service;

import com.wuyang.yygh.model.hosp.Schedule;
import com.wuyang.yygh.vo.hosp.ScheduleOrderVo;
import com.wuyang.yygh.vo.hosp.ScheduleQueryVo;
import com.wuyang.yygh.vo.order.OrderMqVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ScheduleService {
    /**
     * 上传排班
     */
    void saveSchedule(Map<String, Object> stringObjectMap);

    /**
     * 查询排班分页列表
     * @param page
     * @param limit
     * @param scheduleQueryVo
     * @return
     */
    Page<Schedule> selectPage(int page, int limit, ScheduleQueryVo scheduleQueryVo);

    /**
     * 删除排班信息
     */
    void removeScheduleByRequest(Map<String, Object> paramMap);

    /**
     * 查询排班规则
     */
    Map<String, Object> getRuleSchedule(Integer page, Integer limit, String hoscode, String depcode);

    /**
     * 查询排班详细信息
     */
    List<Schedule> getDetailSchedule(String hoscode,String depcode,String workDate);

    Map<String, Object> getBookingScheduleRule(Integer page, Integer limit, String hoscode, String depcode);

    Schedule findAllByScheduleId(String scheduleId);

    ScheduleOrderVo getScheduleOrderVo(String scheduleId);

    void update(OrderMqVo orderMqVo);
}