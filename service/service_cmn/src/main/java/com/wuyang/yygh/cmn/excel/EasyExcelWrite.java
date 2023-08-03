package com.wuyang.yygh.cmn.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class EasyExcelWrite {
    private static List<Student> data(){
        List<Student> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Student data = new Student();
            data.setSno(i);
            data.setSname("张三"+i);
            list.add(data);
        }
        return list;
    }
    public static void main(String[] args) {
        String fileName = "C:\\Users\\23889\\Desktop\\student.xlsx";
        EasyExcel.write(fileName,Student.class).sheet("sheet_wuyang").doWrite(data());
    }
}
