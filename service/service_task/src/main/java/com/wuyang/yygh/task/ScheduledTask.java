package com.wuyang.yygh.task;

import com.wuyang.yygh.rabbit.MqConst;
import com.wuyang.yygh.rabbit.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTask {

    private RabbitService rabbitService;
    //Quartz:cron表达式： 秒 分 时 dayofMonth Month dayofWeek Year【2099】
    @Scheduled(cron = "*/30 * * * * ? ")//从0秒开始，每5秒执行一次
    public void printTime(){
        System.out.println("当前时间为"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_TASK,MqConst.ROUTING_TASK_8,"xxxxxxx");
    }
}
