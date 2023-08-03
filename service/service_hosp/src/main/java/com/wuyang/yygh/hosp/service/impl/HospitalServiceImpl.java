package com.wuyang.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wuyang.yygh.cmn.client.DictFeignClient;
import com.wuyang.yygh.enums.DictEnum;
import com.wuyang.yygh.hosp.repository.HospitalRepository;
import com.wuyang.yygh.hosp.service.HospitalService;
import com.wuyang.yygh.model.hosp.Hospital;
import com.wuyang.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HospitalServiceImpl implements HospitalService {
    @Autowired
    private HospitalRepository hospitalRepository;

    //注入远程调用数据字典
    @Autowired
    private DictFeignClient dictFeignClient;

    @Override
    public void save(Map<String, Object> stringObjectMap) {
        String string = JSONObject.toJSONString(stringObjectMap);//将任意类型的数据转换成字符串类型
        Hospital hospital = JSONObject.parseObject(string, Hospital.class);//将所有的字符串一一对应解析到Hospital中

        //1.根据医院编号查询医院信息
        Hospital mongoHospital = hospitalRepository.findHospByHoscode(hospital.getHoscode());

        if (mongoHospital == null){//2.如果没有这个医院信息，添加操作
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        }else {//3.如果有这个医院信息，进行更新操作
            hospital.setStatus(mongoHospital.getStatus());
            hospital.setCreateTime(mongoHospital.getCreateTime());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(mongoHospital.getIsDeleted());
            hospital.setId(mongoHospital.getId());
            hospitalRepository.save(hospital);
        }

    }

    @Override
    public Hospital findHospitalByHoscode(String hoscode) {
       return hospitalRepository.findHospByHoscode(hoscode);
    }

    //做带条件的分页查询
    @Override
    public Page<Hospital> selectPage(Integer page,Integer limit,HospitalQueryVo hospitalQueryVo) {
        PageRequest pageRequest = PageRequest.of(page-1,limit, Sort.by(Sort.Direction.ASC,"createTime"));//根据创建时间升序排列
        Hospital hospital = new Hospital();
        BeanUtils.copyProperties(hospitalQueryVo,hospital);

        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) //改变默认字符串匹配方式：模糊查询
                .withIgnoreCase(true); //改变默认大小写忽略方式：忽略大小写
        Example example = Example.of(hospital,matcher);
        Page<Hospital> all = hospitalRepository.findAll(example,pageRequest);

        all.getContent().stream().forEach(item -> {
            this.packHospital(item);//远程调用字典模块查询医院等级和全地址
        });
        return all;
    }

    @Override
    public void updateStatus(String id, Integer status) {
        if(status.intValue() == 0 || status.intValue() == 1) {
            Hospital hospital = hospitalRepository.findById(id).get();//排班的状态status是存储在MongoDB中，不是MySQL
            hospital.setStatus(status);
            hospital.setUpdateTime(new Date());
            hospitalRepository.save(hospital);//自动判断添加或者更新，这个MongoDB内置的方法
        }
    }

    @Override
    public Map<String, Object> getHospitalDetailById(String id) {
        Hospital hospital = hospitalRepository.findById(id).get();
        hospital = this.packHospital(hospital);//内部触发OpenFeign远程调用
        Map<String,Object> map = new HashMap<>();
        //医院基本信息（包含医院等级）
        map.put("hospital",hospital);
        //单独处理更直观
        map.put("bookingRule",hospital.getBookingRule());
        //不需要重复返回
        hospital.setBookingRule(null);
        return map;
    }

    @Override
    public Object getHospName(String hoscode) {
        Hospital hospital = hospitalRepository.getHospNameByHoscode(hoscode);
    if (hospital != null) {
            return  hospital.getHosname();
        }
        return null;
    }

    @Override
    public List<Hospital> findByNameLike(String name) {
        return hospitalRepository.findByHosnameLike(name);
    }


    private Hospital packHospital(Hospital item) {
        //这里是医院模块调取字典模块，需要采用远程调用Feign
        String hostype = dictFeignClient.getName(DictEnum.HOSTYPE.getDictCode(), item.getHostype());//查询医院等级要用编号和等级value一起查
        String provinceCode = dictFeignClient.getName(item.getProvinceCode());//省份
        String cityCode = dictFeignClient.getName(item.getCityCode());//市
        String districtCode= dictFeignClient.getName(item.getDistrictCode());//区

        item.getParam().put("hostypeString",hostype);//医院等级
        item.getParam().put("fullAddress", provinceCode+cityCode+districtCode+item.getAddress());
        return item;
    }

}
