package com.wuyang.yygh.cmn.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class DemoDataListener extends AnalysisEventListener<Student> {


    //解析Excel文件的时候执行，而且是逐行解析的
    @Override
    public void invoke(Student student, AnalysisContext analysisContext) {
        System.out.println(student);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("Excel文件的表头信息为："+headMap);
    }

    //excel文件中的数据读取完成之后，要做的事情

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
