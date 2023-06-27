package com.wuyang.yygh.orders.service;

import java.util.Map;

public interface WeixinService {

    /**
     * 根据订单号下单，生成支付链接
     */
    Map createNative(Long orderId) throws Exception;

    /**
     * 根据订单号去微信第三方查询支付状态
     */
    Map<String, String> queryPayStatus(Long orderId, String paymentType);
    /***
     * 退款
     */
    Boolean refund(Long orderId);
}
