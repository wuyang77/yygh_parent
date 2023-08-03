package com.wuyang.yygh.orders.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuyang.yygh.model.order.OrderInfo;
import com.wuyang.yygh.vo.order.OrderCountQueryVo;
import com.wuyang.yygh.vo.order.OrderCountVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author wuyang
 * @since 2023-04-06
 */
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

    //统计每天平台预约数据
    List<OrderCountVo> selectOrderCount(OrderCountQueryVo orderCountQueryVo);
}
