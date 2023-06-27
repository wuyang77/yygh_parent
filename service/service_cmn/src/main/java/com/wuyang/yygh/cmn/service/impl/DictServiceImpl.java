package com.wuyang.yygh.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuyang.yygh.common.exception.YyghException;
import com.wuyang.yygh.cmn.listener.DictListener;
import com.wuyang.yygh.cmn.mapper.DictMapper;
import com.wuyang.yygh.cmn.service.DictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyang.yygh.model.cmn.Dict;
import com.wuyang.yygh.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 组织架构表 服务实现类
 * </p>
 *
 * @author wuyang
 * @since 2023-03-10
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
    @Override
    public void importExcel(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), DictEeVo.class,new DictListener(baseMapper)).sheet().doRead();
    }
    @Override
    public void exportExcel(HttpServletResponse response) throws IOException {
        //导出文件的时候一定要设置2个头信息：Mime-Type、content-type:attachement
        // 这里注意 有同学反应使用swagger会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("字典表", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        List<Dict> dictList = baseMapper.selectList(null);

        List<DictEeVo> dictEeVoList = new ArrayList<DictEeVo>();

        for (Dict dict : dictList) {
            DictEeVo dictEeVo = new DictEeVo();
            BeanUtils.copyProperties(dict,dictEeVo);//这个工具类作用：就是将一个对象的属性值拷贝到另一个对象的属性上，要求：两个对象有相同的属性名
            dictEeVoList.add(dictEeVo);
        }
        EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("sheet_wuyang").doWrite(dictEeVoList);
    }

    @Cacheable(value = "dict")//查询方法上标记：把查询的结果缓存到redis中 dict::pid  key = "'selectIndexList'+#id"
    //@CachePut添加方法：添加数据的时候，也会往redis缓存存储一份
    //@CacheEvict 修改、删除数据的时候，把redis缓存中对应的数据清空
    @Override
    public List<Dict> getDictListByPid(Integer pid) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<Dict>();
        queryWrapper.eq("parent_id",pid);
        List<Dict> dicts = baseMapper.selectList(queryWrapper);
        for (Dict dict : dicts) {
            boolean result = hasChild(dict);//先要判断其是否有子元素
            dict.setHasChildren(result);
        }
        return dicts;
    }
    //判断当前元素是否有下一级子元素，有就返回true,没有就返回fasle
    private boolean hasChild(Dict dict){
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<Dict>();
        queryWrapper.eq("parent_id",dict.getId());
        Integer count = baseMapper.selectCount(queryWrapper);//查出是否有子元素，且查出子元素的个数
        return count>0;
    }

    @Override
    public String getNameByParentDictCodeAndValue(String parentDictCode, String value) {

        //判断parentDictCode是否为空
        //1.如果为空，就只根据value查询
        if (StringUtils.isEmpty(parentDictCode)){
            QueryWrapper<Dict> queryWrapper = new QueryWrapper<Dict>();
            queryWrapper.eq("value",value);
            Dict dict = baseMapper.selectOne(queryWrapper);
            if (dict != null) {
                return dict.getName();
            }
        }else {
            //2.如果不为空,先通过parentDictCode把dict对象查询出来，dict中id和value查询
            QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
            dictQueryWrapper.eq("dict_code",parentDictCode);
            Dict dict = baseMapper.selectOne(dictQueryWrapper);
            if (dict == null){
                throw new YyghException(20001,"查询失败");
            }
            Long parentId = dict.getId();
            QueryWrapper<Dict> newWrapper = new QueryWrapper<>();
            newWrapper.eq("parent_id",parentId);
            newWrapper.eq("value",value);
            Dict newDict = baseMapper.selectOne(newWrapper);
            if (newDict != null) {
                return newDict.getName();
            }
        }
        return null;
    }

    @Override
    public List<Dict> findByDictCode(String dictCode) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<Dict>();
        queryWrapper.eq("dict_code",dictCode);
        Dict dict = baseMapper.selectOne(queryWrapper);
        if (dict != null){
            Long parentId = dict.getId();
            QueryWrapper<Dict> parentWrapper = new QueryWrapper<Dict>();
            parentWrapper.eq("parent_id",parentId);
            List<Dict> dicts = baseMapper.selectList(parentWrapper);
            return dicts;
        }
        return null;
    }

}
