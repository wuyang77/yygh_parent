package com.wuyang.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyang.yygh.model.cmn.Dict;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * <p>
 * 组织架构表 服务类
 * </p>
 *
 * @author wuyang
 * @since 2023-03-10
 */
public interface DictService extends IService<Dict> {

    /**
     *导入Excel表格
     */
    void importExcel(MultipartFile file) throws IOException;
    /**
     *导出Excel表格
     */
    void exportExcel(HttpServletResponse response) throws IOException;

    /**
     * 根据父id查询子元素列表
     */
    List<Dict> getDictListByPid(Integer pid);

    /**
     * 根据上级编码与值（或者只有值）获取数据字典名称
     */
    String getNameByParentDictCodeAndValue(String parentDictCode, String value);

    /**
     * 根据dict_code查询所有的省份信息
     */
    List<Dict> findByDictCode(String dictCode);
}
