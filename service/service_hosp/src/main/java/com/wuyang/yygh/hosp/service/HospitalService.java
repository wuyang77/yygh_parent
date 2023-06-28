package com.wuyang.yygh.hosp.service;

import com.wuyang.yygh.model.hosp.Hospital;
import com.wuyang.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Map;

public interface HospitalService {
    void save(Map<String, Object> stringObjectMap);
    /**
     * 根据医院编码查询医院对象
     */
    Hospital findHospitalByHoscode(String hoscode);

    Page<Hospital> selectPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo);

    void updateStatus(String id, Integer status);

    Map<String, Object> getHospitalDetailById(String id);

    Object getHospName(String hoscode);

    List<Hospital> findByNameLike(String name);
}
