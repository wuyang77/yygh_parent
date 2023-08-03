package com.wuyang.yygh.hosp.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuyang.yygh.common.result.R;
import com.wuyang.yygh.hosp.service.HospitalSetService;
import com.wuyang.yygh.hosp.util.MD5;
import com.wuyang.yygh.model.hosp.HospitalSet;
import com.wuyang.yygh.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * 医院设置表 前端控制器
 * </p>
 *
 * @author wuyang
 * @since 2023-02-26
 */

@Slf4j
@Api(tags = "后台管理预约医院设置接口")
@RestController
@RequestMapping("/admin/hosp/hospital-set")
//@CrossOrigin
public class HospitalSetController {
    @Autowired
    private HospitalSetService hospitalSetService;
    //医院添加
    @ApiOperation(value = "后台添加医院")
    @PostMapping ("/saveHospSet")
    public R save(@RequestBody HospitalSet hospitalSet){
        //这里签名秘钥和状态需要自己设置，因为在前端传入后端的时候，所传进来的HospitalSet没有这两个属性值
        //设置状态 1 使用 0 不能使用
        hospitalSet.setStatus(1);
        //签名秘钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+random.nextInt(1000)));//传入一个系统当前时间拼接1000以内的随机数字符串，然后采用MD5算法进行加密
        hospitalSetService.save(hospitalSet);//HospitalServcie继承了Iservie,这是save方法是Iservice内置的方法，该方法有苞米豆公司提供的。
        return R.ok();
    }

    @ApiOperation("根据医院id查询医院详情")
    @GetMapping("/detail/{id}")
    public R getHospitalSetDetail(@PathVariable("id")Integer id){
        return R.ok().data("item",hospitalSetService.getById(id));
    }
    @ApiOperation("后台修改医院")
    @PutMapping("/updateHospSet")
    public R getHospitalSetDetail(@RequestBody HospitalSet hospitalSet){
        hospitalSetService.updateById(hospitalSet);
        return R.ok();
    }

    //批量删除医院设置
    @ApiOperation("批量的删除医院")
    @DeleteMapping(value = "/batchRemove")
    public R batchRemoveHospitalSet(@RequestBody List<Long> idList) {
        hospitalSetService.removeByIds(idList);
        return R.ok();
    }

    @ApiOperation("医院设置锁定和解锁")
    @PutMapping("/lockHospitalSet/{id}/{status}")
    public R lockHospitalSet(@PathVariable Long id,
                             @PathVariable Integer status) {
        //根据id查询医院设置信息
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        //设置状态
        hospitalSet.setStatus(status);
        //调用方法
        hospitalSetService.updateById(hospitalSet);
        return R.ok();
    }

    /*
        @Api:标记在controller类上
        @ApiOperation:标记在controller类的方法上
        @ApiParam：标记在controller类方法的参数上
        @ApiModel:标记在pojo类上
        @ApiModelProperty:标记在pojo类的属性上
     */
    //swagger是接口文档生成|接口测试信息
    @ApiOperation(value = "查询所有的医院信息")
    @GetMapping("/findAll")
    private R findAll(@ApiParam(name = "sid",value = "医院id",required = true)@RequestParam(value = "name")String name){
        List<HospitalSet> list = hospitalSetService.list();
        return R.ok().data("items",list);
    }
    @ApiOperation(value = "查询医院的id删除医院信息")
    @DeleteMapping("/remove/{id}")
    public R removeById(@ApiParam(name = "id",value = "id",required = true)@PathVariable(value = "id") Integer id){
        boolean b = hospitalSetService.removeById(id);
        if (b){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "查询医院的分页信息")
    @GetMapping("/{current}/{limit}")
    public R pageList(@PathVariable(value = "current")Integer current,
                      @PathVariable(value = "limit")Integer limit){
        Page page = new Page(current, limit);
        hospitalSetService.page(page);
        return R.ok().data("total",page.getTotal()).data("rows",page.getRecords());//获取总页数和当前页的所有数据
    }
    @ApiOperation(value = "分页条件医院设置列表")
    @PostMapping("/{current}/{limit}")
    public R pageQuery(
            @ApiParam(name = "current", value = "当前页码", required = true)
            @PathVariable("current")Long current,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable("limit")Long limit,

            @ApiParam(name = "hospitalSetQueryVo", value = "查询对象", required = false)
            @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo){

        Page page = new Page(current, limit);
        //注意：针对hospitalSetQueryVo这个实体对象的非空判断完全没有必要，因为这个对象永远不可能为null,就算不给后端传，这个变量也会有地址值
        if (hospitalSetQueryVo == null){
            hospitalSetService.page(page);
        } else {
            //所以只写else这一部分就可以了，上面的if部分可以删除
            String hosname = hospitalSetQueryVo.getHosname();
            String hoscode = hospitalSetQueryVo.getHoscode();
            QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
            if (!StringUtils.isEmpty(hosname)) {
               wrapper.like("hosname", hosname);
            }
            if (!StringUtils.isEmpty(hoscode) ) {
                wrapper.eq("hoscode", hoscode);
            }
            hospitalSetService.page(page,wrapper);
        }

        List<HospitalSet> records = page.getRecords();
        long total = page.getTotal();
        return  R.ok().data("total", total).data("rows", records);
    }


}

