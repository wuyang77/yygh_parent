package com.wuyang.yygh.task;

import com.wuyang.yygh.rabbit.MqConst;
import com.wuyang.yygh.rabbit.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 就医提醒发送病人手机号短信信息，每5秒执行一次
 */
@Component
public class ScheduledTask {

    @Autowired
    private RabbitService rabbitService;
    //Quartz:cron表达式： 秒 分 时 dayofMonth Month dayofWeek Year【2099】
    @Scheduled(cron = "*/50 * * * * ?")//从0秒开始，每5秒执行一次
    public void printTime(){
        System.out.println(new Date().toLocaleString());
        rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_TASK,MqConst.ROUTING_TASK_8,"xxxxxxx");
    }
}
