package com.wuyang.yygh.hosp.repository;

import com.wuyang.yygh.model.hosp.Department;
import com.wuyang.yygh.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends MongoRepository<Hospital,String> {

    Hospital getHospNameByHoscode(String hoscode);

    List<Hospital> findByHosnameLike(String name);

    /**
     * 根据医院编码查询医院对象
     */
    Hospital findHospByHoscode(String hoscode);
}