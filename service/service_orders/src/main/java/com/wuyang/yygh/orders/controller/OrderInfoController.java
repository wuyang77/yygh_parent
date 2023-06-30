package com.wuyang.yygh.orders.controller;


import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowItem;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuyang.yygh.common.result.R;
import com.wuyang.yygh.enums.OrderStatusEnum;
import com.wuyang.yygh.model.order.OrderInfo;
import com.wuyang.yygh.orders.service.OrderInfoService;
import com.wuyang.yygh.orders.utils.AuthContextHolder;
import com.wuyang.yygh.vo.order.OrderCountQueryVo;
import com.wuyang.yygh.vo.order.OrderQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author wuyang
 * @since 2023-04-06
 */

//创建controller方法
@Api(tags = "订单接口")
@RestController
@RequestMapping("/api/order/orderInfo")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    //构造方法
    public OrderInfoController(){
        initRule();
    }

    /**
     * 导入热点值限流规则
     * 也可在Sentinel dashboard界面配置（仅测试）
     */
    public void initRule() {
        ParamFlowRule pRule = new ParamFlowRule("submitOrder")//资源名称，与SentinelResource值保持一致
                //限流第一个参数
                .setParamIdx(0)
                //单机阈值
                .setCount(5);

        // 针对 热点参数值单独设置限流 QPS 阈值，而不是全局的阈值.
        //如：1000（北京协和医院）,可以通过数据库表一次性导入，目前为测试
        ParamFlowItem item1 = new ParamFlowItem().setObject("1000")//热点值
                .setClassType(String.class.getName())//热点值类型
                .setCount(1);//热点值 QPS 阈值
        List<ParamFlowItem> list = new ArrayList<>();
        list.add(item1);
        pRule.setParamFlowItemList(list);
        ParamFlowRuleManager.loadRules(Collections.singletonList(pRule));
    }

    @ApiOperation(value = "获取订单统计数据")
    @PostMapping("inner/getCountMap")
    public Map<String, Object> getCountMap(@RequestBody OrderCountQueryVo orderCountQueryVo) {
        return orderInfoService.getCountMap(orderCountQueryVo);
    }

    //订单列表（条件查询带分页）
    @GetMapping("auth/{page}/{limit}")
    public R list(@PathVariable Long page,
                  @PathVariable Long limit,
                  OrderQueryVo orderQueryVo, HttpServletRequest request) {
        //设置当前用户id
        orderQueryVo.setUserId(AuthContextHolder.getUserId(request));
        Page<OrderInfo> pageParam = new Page<>(page,limit);
        IPage<OrderInfo> pageModel = orderInfoService.selectPage(pageParam,orderQueryVo);
        return R.ok().data("pageModel",pageModel);
    }

    @ApiOperation(value = "获取订单状态")
    @GetMapping("/auth/getStatusList")
    public R getStatusList() {
        return R.ok().data("statusList", OrderStatusEnum.getStatusList());
    }
    //根据订单id查询订单详情
    @GetMapping("/auth/getOrders/{orderId}")
    public R getOrderDetail(@PathVariable Long orderId) {
        OrderInfo orderInfo = orderInfoService.getOrderInfo(orderId);
        return R.ok().data("orderInfo",orderInfo);
    }

    @ApiOperation(value = "创建订单")
    @PostMapping("/auth/submitOrder/{scheduleId}/{patientId}")
    public R createOrder(
            @ApiParam(name = "scheduleId",value = "排班id",required = true)
            @PathVariable(value = "scheduleId")String scheduleId,
            @ApiParam(name = "patientId",value = "就诊人id",required = true)
            @PathVariable(value = "patientId")Long patientId) {

        String orderId = orderInfoService.saveOrder(scheduleId,patientId);
        return R.ok().data("orderId",orderId);
    }

    @ApiOperation(value = "取消预约")
    @GetMapping("/auth/cancelOrder/{orderId}")
    public R cancelOrder(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @PathVariable("orderId") Long orderId){
        Boolean flag = orderInfoService.cancelOrder(orderId);
        return R.ok().data("flag",flag);
    }

    /**
     * 热点值超过 QPS 阈值，返回结果
     * @param hoscode
     * @param scheduleId
     * @param patientId
     * @param e
     * @return
     */
    public R submitOrderBlockHandler(String hoscode, String scheduleId, Long patientId, BlockException e){
        return R.error().message("系统业务繁忙，请稍后下单");
    }
}

