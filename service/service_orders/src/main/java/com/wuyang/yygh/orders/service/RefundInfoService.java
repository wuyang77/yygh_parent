package com.wuyang.yygh.orders.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyang.yygh.model.order.PaymentInfo;
import com.wuyang.yygh.model.order.RefundInfo;

/**
 * <p>
 * 退款信息表 服务类
 * </p>
 *
 * @author wuyang
 * @since 2023-04-12
 */
public interface RefundInfoService extends IService<RefundInfo> {
    /**
     * 保存退款记录
     * @param paymentInfo
     */
    RefundInfo saveRefundInfo(PaymentInfo paymentInfo);
}
