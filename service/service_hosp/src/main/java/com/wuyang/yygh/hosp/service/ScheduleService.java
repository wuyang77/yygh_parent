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
     * 上传排班(注意这里是医院管理平台远程发起的请求)
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
     * 查询排班分页，根据条件，分组，排序的MonogDB的聚合查询
     */
    Map<String, Object> getRuleSchedule(Integer page, Integer limit, String hoscode, String depcode);

    /**
     * 查询排班详细信息
     */
    List<Schedule> getDetailSchedule(String hoscode,String depcode,String workDate);

    /**
     * 获取可预约的排班数据
     * @param page
     * @param limit
     * @param hoscode
     * @param depcode
     * @return
     */
    Map<String, Object> getBookingScheduleRule(Integer page, Integer limit, String hoscode, String depcode);

    /**
     * 订单页面触发的，根据排排班id获取排班
     * @param scheduleId
     * @return
     */
    Schedule findAllByScheduleId(String scheduleId);

    /**
     * 订单页面触发的，根据排班id获取订单封装对象
     * @param scheduleId
     * @return
     */
    ScheduleOrderVo getScheduleOrderVo(String scheduleId);

    /**
     * 更新订单封装对象里面的预约信息
     * @param
     */
    void update(OrderMqVo orderMqVo);
}