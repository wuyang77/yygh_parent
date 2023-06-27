package com.wuyang.yygh.hosp.repository;

import com.wuyang.yygh.model.hosp.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepositroy extends MongoRepository<Department,String> {
    Department findByHoscodeAndDepcode(String hoscode, String depcode);

    Department getDepartmentByHoscodeAndDepcode(String hoscode, String depcode);
}
