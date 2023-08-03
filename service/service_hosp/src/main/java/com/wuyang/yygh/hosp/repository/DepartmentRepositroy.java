package com.wuyang.yygh.hosp.repository;

import com.wuyang.yygh.model.hosp.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepositroy extends MongoRepository<Department,String> {
    /**
     * 根据医院编号查询医院科室信息
     */
    Department findDepartmentByHoscodeAndDepcode(String hoscode, String depcode);

    Department getDepartmentByHoscodeAndDepcode(String hoscode, String depcode);
}
