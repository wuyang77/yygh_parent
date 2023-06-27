package com.wuyang.yygh.orders.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyang.yygh.common.result.R;
import com.wuyang.yygh.enums.OrderStatusEnum;
import com.wuyang.yygh.enums.PaymentStatusEnum;
import com.wuyang.yygh.enums.PaymentTypeEnum;
import com.wuyang.yygh.model.order.OrderInfo;
import com.wuyang.yygh.model.order.PaymentInfo;
import com.wuyang.yygh.orders.mapper.PaymentMapper;
import com.wuyang.yygh.orders.service.OrderInfoService;
import com.wuyang.yygh.orders.service.PaymentService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, PaymentInfo> implements PaymentService {

    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private PaymentService paymentService;
    /**
     * 保存交易记录
     * @param order
     * @param paymentType 支付类型（1：微信 2：支付宝）
     */
    //添加交易记录
    @Override
    public void savePaymentInfo(OrderInfo order, Integer paymentType) {
        //如果该订单已经支付过了，不用支付了
        QueryWrapper<PaymentInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", order.getId());
        queryWrapper.eq("payment_type", paymentType);
        Integer count = baseMapper.selectCount(queryWrapper);
        if(count >0) return;
        // 保存交易记录
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setCreateTime(new Date());
        paymentInfo.setOrderId(Long.valueOf(order.getId()));
        paymentInfo.setPaymentType(paymentType);
        paymentInfo.setOutTradeNo(order.getOutTradeNo());
        paymentInfo.setPaymentStatus(PaymentStatusEnum.UNPAID.getStatus());
        String subject = new DateTime(order.getReserveDate()).toString("yyyy-MM-dd")+"|"+order.getHosname()+"|"+order.getDepname()+"|"+order.getTitle();
        paymentInfo.setSubject(subject);
        paymentInfo.setTotalAmount(order.getAmount());
        baseMapper.insert(paymentInfo);
    }

    @Override
    public void paySuccess(String outTradeNo, Integer status, Map<String, String> resultMap) {
        //1.更新订单状态（根据商品订单号查询订单信息对象，然后设置订单对象的属性，并做更新操作）
        QueryWrapper<OrderInfo> wrapperOrder = new QueryWrapper<>();
        wrapperOrder.eq("out_trade_no",outTradeNo);
        OrderInfo orderInfo = orderInfoService.getOne(wrapperOrder);
        orderInfo.setOrderStatus(OrderStatusEnum.PAID.getStatus());//设置订单的状态
        orderInfoService.updateById(orderInfo);//更新操作

        //2.更新支付记录状态（根据商品订单号查询支付信息对象，然后设置支付信息对象的属性，并做更新操作）
        QueryWrapper<PaymentInfo> wrapperPayment = new QueryWrapper<>();
        wrapperPayment.eq("out_trade_no",outTradeNo);
        PaymentInfo paymentInfo = baseMapper.selectOne(wrapperPayment);
        //设置状态
        paymentInfo.setPaymentStatus(PaymentStatusEnum.PAID.getStatus());
        paymentInfo.setTradeNo(resultMap.get("transaction_id"));//设置微信订单号
        paymentInfo.setCallbackTime(new Date());//设置回滚的时间
        paymentInfo.setCallbackContent(resultMap.toString());//设置回滚的内容
        baseMapper.updateById(paymentInfo);//更新操作
    }
}