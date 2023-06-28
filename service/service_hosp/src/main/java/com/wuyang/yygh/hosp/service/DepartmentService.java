package com.wuyang.yygh.hosp.service;

import com.wuyang.yygh.model.hosp.Department;
import com.wuyang.yygh.vo.hosp.DepartmentVo;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface DepartmentService {
    /**
     * 添加医院科室
     */
    void saveDepartment(Map<String, Object> stringObjectMap);

    /**
     * 获取医院科室分页列表
     */
    Page findDeparmentPage(Map<String, Object> stringObjectMap);

    /**
     * 根据医院编号和科室编号删除医院科室
     */
    void removeDepartment(String hoscode, String depcode);
    List<DepartmentVo> findDeptTree(String hoscode);

    //根据科室编号，和医院编号，查询科室名称
    String getDepName(String hoscode, String depcode);


    /**
     * 根据医院编号 和 科室编号获取科室数据
     */
    Department getDepartment(String hoscode, String depcode);


}
