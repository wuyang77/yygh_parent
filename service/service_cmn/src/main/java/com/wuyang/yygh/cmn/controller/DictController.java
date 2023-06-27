package com.wuyang.yygh.cmn.controller;


import com.wuyang.yygh.cmn.service.DictService;
import com.wuyang.yygh.common.result.R;
import com.wuyang.yygh.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 组织架构表 前端控制器
 * </p>
 *
 * @author wuyang
 * @since 2023-03-10
 */
@Api(tags = "字典表接口")
@RestController
@RequestMapping("/admin/cmn/dict")
//@CrossOrigin
public class DictController {
    @Autowired
    private DictService dictService;

    @ApiOperation("导出Excel")
    @GetMapping ("/exportData")
    public void exportExcel(HttpServletResponse response) throws IOException {
        dictService.exportExcel(response);
    }
    @ApiOperation("导入Excel")
    @PostMapping("/importData")
    public R importData(MultipartFile file) throws IOException {
        dictService.importExcel(file);
        return R.ok();
    }
    @ApiOperation(value = "根据dict_code查询所有的省份信息")
    @GetMapping(value = "/findByDictCode/{dictCode}")
    public R findByDictCode(
            @ApiParam(name = "dictCode", value = "字典编码", required = true)
            @PathVariable("dictCode") String dictCode) {
        List<Dict> list = dictService.findByDictCode(dictCode);
        return R.ok().data("list",list);
    }
    @ApiOperation(value = "根据父id查询子元素列表")
    @GetMapping("/childList/{pid}")
    public R getDictListPid(@PathVariable("pid")Integer pid){
        List<Dict> dictList = dictService.getDictListByPid(pid);
        return R.ok().data("list",dictList);
    }
    @ApiOperation(value = "获取数据字典名称")
    @GetMapping(value = "/getName/{parentDictCode}/{value}")
    public String getName(
            @ApiParam(name = "parentDictCode", value = "上级编码", required = true)
            @PathVariable("parentDictCode") String parentDictCode,
            @ApiParam(name = "value", value = "值", required = true)
            @PathVariable("value") String value) {
        return dictService.getNameByParentDictCodeAndValue(parentDictCode, value);
    }

    @ApiOperation(value = "获取数据字典名称")
    @GetMapping(value = "/getName/{value}")
    public String getName(
            @ApiParam(name = "value", value = "值", required = true)
            @PathVariable("value") String value) {
        return dictService.getNameByParentDictCodeAndValue("", value);
    }
}

