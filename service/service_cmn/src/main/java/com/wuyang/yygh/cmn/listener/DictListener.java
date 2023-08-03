package com.wuyang.yygh.cmn.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.wuyang.yygh.cmn.mapper.DictMapper;
import com.wuyang.yygh.model.cmn.Dict;
import com.wuyang.yygh.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class DictListener extends AnalysisEventListener<DictEeVo> {


    private DictMapper dictMapper;
    private List<Dict> list = new ArrayList<>(BATCH_COUNT);
    private static final int BATCH_COUNT = 5;

    public DictListener(DictMapper dictMapper) {
        // 这里是demo，所以随便new一个。实际使用如果到了spring,请使用下面的有参构造函数
        this.dictMapper=dictMapper;
    }
    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictEeVo,dict);
        dictMapper.insert(dict);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
