package com.wuyang.yygh.orders.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuyang.yygh.enums.RefundStatusEnum;
import com.wuyang.yygh.model.order.PaymentInfo;
import com.wuyang.yygh.model.order.RefundInfo;
import com.wuyang.yygh.orders.mapper.RefundInfoMapper;
import com.wuyang.yygh.orders.service.RefundInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 退款信息表 服务实现类
 * </p>
 *
 * @author wuyang
 * @since 2023-04-12
 */
@Service
public class RefundInfoServiceImpl extends ServiceImpl<RefundInfoMapper, RefundInfo> implements RefundInfoService {

    @Override
    public RefundInfo saveRefundInfo(PaymentInfo paymentInfo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id",paymentInfo.getOrderId());
        RefundInfo refundInfo = baseMapper.selectOne(queryWrapper);
        if (refundInfo != null){//如果表里面已经有了该订单号的交易信息就直接返回自己
            return refundInfo;
        }
        // 保存退款记录
        refundInfo = new RefundInfo();
        refundInfo.setCreateTime(new Date());//创建时间
        refundInfo.setOrderId(paymentInfo.getOrderId());//订单编号
        refundInfo.setPaymentType(paymentInfo.getPaymentType());//支付类型（微信 支付宝）
        refundInfo.setOutTradeNo(paymentInfo.getOutTradeNo());//对外业务编号
        //refundInfo.setTradeNo(paymentInfo.getTradeNo());//交易编号
        refundInfo.setRefundStatus(RefundStatusEnum.UNREFUND.getStatus());//退款状态
        refundInfo.setSubject(paymentInfo.getSubject());//交易内容
        //paymentInfo.setSubject("test");
        refundInfo.setTotalAmount(paymentInfo.getTotalAmount());//支付金额
        baseMapper.insert(refundInfo);
        return refundInfo;
    }
}
