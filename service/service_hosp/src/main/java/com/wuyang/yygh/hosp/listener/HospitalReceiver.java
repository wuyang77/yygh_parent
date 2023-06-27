package com.wuyang.yygh.hosp.listener;

import com.rabbitmq.client.Channel;
import com.wuyang.yygh.hosp.service.ScheduleService;
import com.wuyang.yygh.model.hosp.Schedule;
import com.wuyang.yygh.rabbit.MqConst;
import com.wuyang.yygh.rabbit.RabbitService;
import com.wuyang.yygh.vo.msm.MsmVo;
import com.wuyang.yygh.vo.order.OrderMqVo;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class HospitalReceiver {
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private RabbitService rabbitService;
    @RabbitListener(bindings = {
            @QueueBinding(value =@Queue(name = MqConst.QUEUE_ORDER,durable = "true"),
                                        exchange = @Exchange(name = MqConst.EXCHANGE_DIRECT_ORDER),
                                        key = MqConst.ROUTING_ORDER
            )
    })
    public void consume(OrderMqVo orderMqVo, Message message, Channel channel){
        //下单成功更新预约数
        scheduleService.update(orderMqVo);
        //发送短信
        MsmVo msmVo = orderMqVo.getMsmVo();
        rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_MSM, MqConst.ROUTING_MSM_ITEM, msmVo);
    }
}
