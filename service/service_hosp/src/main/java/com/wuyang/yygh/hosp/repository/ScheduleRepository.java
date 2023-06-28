package com.wuyang.yygh.hosp.repository;

import com.wuyang.yygh.model.hosp.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ScheduleRepository extends MongoRepository<Schedule,String> {

    /**
     * 根据医院编号和医院排班id查询医院排班
     * @param hoscode
     * @param hosScheduleId
     * @return
     */
    Schedule findScheduleByHoscodeAndHosScheduleId(String hoscode, String hosScheduleId);

    List<Schedule> findScheduleByHoscodeAndDepcodeAndWorkDate(String hoscode, String depcode, Date toDate);

    Schedule findScheduleByHosScheduleId(String scheduleId);
}