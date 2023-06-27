package com.wuyang.yygh.orders.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyang.yygh.model.order.OrderInfo;
import com.wuyang.yygh.model.order.PaymentInfo;

import java.util.List;
import java.util.Map;

public interface PaymentService extends IService<PaymentInfo> {
    /**
     * 保存交易记录
     */
    void savePaymentInfo(OrderInfo orderInfo, Integer paymentType);

    /**
     * 更新支付状态
     * @param outTradeNo 交易号
     * @param status 支付类型 微信 支付宝
     * @param resultMap 调用微信查询支付状态接口返回map集合
     */
    void paySuccess(String outTradeNo, Integer status, Map<String, String> resultMap);
}