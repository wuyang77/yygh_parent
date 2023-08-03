package com.wuyang.yygh.hosp.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.wuyang.yygh.hosp.repository.DepartmentRepositroy;
import com.wuyang.yygh.hosp.service.DepartmentService;
import com.wuyang.yygh.model.hosp.Department;
import com.wuyang.yygh.vo.hosp.DepartmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepositroy departmentRepositroy;
    @Override
    public void saveDepartment(Map<String, Object> stringObjectMap) {
        String string = JSONObject.toJSONString(stringObjectMap);//将任意类型的数据转换成字符串类型
        Department department = JSONObject.parseObject(string, Department.class);//将所有的字符串一一对应解析到Hospital中

        //1.查询MongoDB中是否有科室信息？
        Department targetDepartment = departmentRepositroy.findDepartmentByHoscodeAndDepcode(department.getHoscode(),department.getDepcode());
        if (targetDepartment == null){//2.无，做添加操作
            department.setCreateTime(new Date());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            departmentRepositroy.save(department);
        }else {//3.有，做更新操作
            department.setCreateTime(targetDepartment.getCreateTime());
            department.setUpdateTime(new Date());
            department.setIsDeleted(department.getIsDeleted());
            department.setId(targetDepartment.getId());
            departmentRepositroy.save(department);
        }
    }

    @Override
    public Page findDeparmentPage(Map<String, Object> stringObjectMap) {

        String hoscode = (String) stringObjectMap.get("hoscode");
        Integer page = Integer.valueOf((String) stringObjectMap.get("page"));
        Integer limit = Integer.valueOf((String) stringObjectMap.get("limit"));
        Department department = new Department();
        department.setHoscode(hoscode);

        Example example = Example.of(department);
        PageRequest pageRequest = PageRequest.of(page-1,limit,Sort.by(Sort.Direction.ASC,"createTime"));

        return departmentRepositroy.findAll(example,pageRequest);
    }

    @Override
    public void removeDepartment(String hoscode, String depcode) {
        Department department = departmentRepositroy.findDepartmentByHoscodeAndDepcode(hoscode,depcode);//先根据医院编号和科室编号查得所在科室
        if (department != null) {//如果科室不为空，则删除科室，就这么简单
            departmentRepositroy.deleteById(department.getId());
        }
    }

    @Override
    public List<DepartmentVo> findDeptTree(String hoscode) {
        //创建list集合，用于最终数据封装
        List<DepartmentVo> result = new ArrayList<>();

        //根据医院编号，查询医院所有科室信息
        Department departmentQuery = new Department();
        departmentQuery.setHoscode(hoscode);
        Example example = Example.of(departmentQuery);//按示例（按例子）查询
        //所有科室列表 departmentList
        List<Department> departmentList = departmentRepositroy.findAll(example);

        //根据大科室编号  bigcode 分组，获取每个大科室里面下级子科室
        Map<String, List<Department>> deparmentMap = departmentList.stream().collect(Collectors.groupingBy(Department::getBigcode));
        //遍历map集合 deparmentMap
        for(Map.Entry<String,List<Department>> entry : deparmentMap.entrySet()) {
            //大科室编号
            String bigcode = entry.getKey();
            //大科室编号对应的全局数据（小科室的集合）
            List<Department> deparment1List = entry.getValue();
            //封装大科室
            DepartmentVo departmentVo1 = new DepartmentVo();
            departmentVo1.setDepcode(bigcode);
            departmentVo1.setDepname(deparment1List.get(0).getBigname());

            //封装小科室
            List<DepartmentVo> children = new ArrayList<>();
            for(Department department: deparment1List) {
                DepartmentVo departmentVo2 =  new DepartmentVo();
                departmentVo2.setDepcode(department.getDepcode());
                departmentVo2.setDepname(department.getDepname());
                //封装到list集合
                children.add(departmentVo2);
            }
            //把小科室list集合放到大科室children里面
            departmentVo1.setChildren(children);
            //放到最终result里面
            result.add(departmentVo1);
        }
//        封装的集合大概得样子
//        departmentMapVo:{
//            deparmentMapVo1:{
//                children:{
//                    deparmentVo2:{
//                        ...
//                    }
//                }
//            }
//        }
        //返回
        return result;
    }

    //根据科室编号，和医院编号，查询科室名称
    @Override
    public String getDepName(String hoscode,String depcode) {
        Department department = departmentRepositroy.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if(department != null) {
            return department.getDepname();
        }
        return null;
    }
    //实现方法：根据医院编号 和 科室编号获取科室数据
    @Override
    public Department getDepartment(String hoscode, String depcode) {
        return departmentRepositroy.getDepartmentByHoscodeAndDepcode(hoscode,depcode);
    }
}
