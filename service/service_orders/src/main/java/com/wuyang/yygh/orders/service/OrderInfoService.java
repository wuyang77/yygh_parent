package com.wuyang.yygh.orders.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyang.yygh.model.order.OrderInfo;
import com.wuyang.yygh.vo.order.OrderCountQueryVo;
import com.wuyang.yygh.vo.order.OrderCountVo;
import com.wuyang.yygh.vo.order.OrderQueryVo;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author wuyang
 * @since 2023-04-06
 */
public interface OrderInfoService extends IService<OrderInfo> {
    /**
     * 保存订单
     */
    String saveOrder(String scheduleId, Long patientId);

    /**
     * 分页列表
     */
    IPage<OrderInfo> selectPage(Page<OrderInfo> pageParam, OrderQueryVo orderQueryVo);
    /**
     * 获取订单详情
     */
    OrderInfo getOrderInfo(Long orderId);
    /**
     * 取消订单
     */
    Boolean cancelOrder(Long orderId);

    /**
     * 病人短信消息提醒
     */
    void patientTips();

    /**
     * 订单统计
     */
    Map<String, Object> getCountMap(OrderCountQueryVo orderCountQueryVo);
}
