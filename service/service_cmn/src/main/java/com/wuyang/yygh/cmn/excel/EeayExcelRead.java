package com.wuyang.yygh.cmn.excel;

import com.alibaba.excel.EasyExcel;

public class EeayExcelRead {
    public static void main(String[] args) {
        EasyExcel.read("C:\\Users\\23889\\Desktop\\student.xlsx",Student.class,new DemoDataListener()).sheet("sheet_wuyang").doRead();
    }
}
