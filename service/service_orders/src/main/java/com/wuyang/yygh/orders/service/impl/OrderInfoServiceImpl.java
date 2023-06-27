package com.wuyang.yygh.orders.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import com.wuyang.yygh.common.exception.YyghException;
import com.wuyang.yygh.common.result.R;
import com.wuyang.yygh.enums.OrderStatusEnum;
import com.wuyang.yygh.enums.PaymentStatusEnum;
import com.wuyang.yygh.hosp.client.HospitalFeignClient;
import com.wuyang.yygh.model.order.OrderInfo;
import com.wuyang.yygh.model.order.PaymentInfo;
import com.wuyang.yygh.model.user.Patient;
import com.wuyang.yygh.orders.mapper.OrderInfoMapper;
import com.wuyang.yygh.orders.service.OrderInfoService;
import com.wuyang.yygh.orders.service.PaymentService;
import com.wuyang.yygh.orders.service.WeixinService;
import com.wuyang.yygh.orders.utils.ConstantPropertiesUtils;
import com.wuyang.yygh.orders.utils.HttpClient;
import com.wuyang.yygh.orders.utils.HttpRequestHelper;
import com.wuyang.yygh.rabbit.MqConst;
import com.wuyang.yygh.rabbit.RabbitService;
import com.wuyang.yygh.result.ResultCodeEnum;
import com.wuyang.yygh.user.client.PatientFeignClient;
import com.wuyang.yygh.vo.hosp.ScheduleOrderVo;
import com.wuyang.yygh.vo.msm.MsmVo;
import com.wuyang.yygh.vo.order.OrderCountQueryVo;
import com.wuyang.yygh.vo.order.OrderCountVo;
import com.wuyang.yygh.vo.order.OrderMqVo;
import com.wuyang.yygh.vo.order.OrderQueryVo;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author wuyang
 * @since 2023-04-06
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {
    @Autowired
    private PatientFeignClient patientFeignClient;

    @Autowired
    private HospitalFeignClient hospitalFeignClient;

    @Autowired
    private RabbitService rabbitService;
    @Autowired
    private WeixinService weixinService;
    @Autowired
    private PaymentService paymentService;

    @Override
    public String saveOrder(String scheduleId,Long patientId) {
        //1.根据scheduleId获取医生排版信息
        ScheduleOrderVo scheduleOrderVo = hospitalFeignClient.getScheduleOrderVo(scheduleId);
        //2.根据patientId获取就诊人信息
        R r= patientFeignClient.getPatient(patientId);

        Patient patient = JSONObject.parseObject(JSONObject.toJSONString(r.getData().get("patient")), Patient.class);
        //3.平台系统调用第三方医院系统：确认是否还能挂号
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("hoscode",scheduleOrderVo.getHoscode());
        paramMap.put("depcode",scheduleOrderVo.getDepcode());
        paramMap.put("hosScheduleId",scheduleOrderVo.getHosScheduleId());
        paramMap.put("reserveDate",new DateTime(scheduleOrderVo.getReserveDate()).toString("yyyy-MM-dd"));
        paramMap.put("reserveTime", scheduleOrderVo.getReserveTime());
        paramMap.put("amount",scheduleOrderVo.getAmount()); //挂号费用
        paramMap.put("name", patient.getName());
        paramMap.put("certificatesType",patient.getCertificatesType());
        paramMap.put("certificatesNo", patient.getCertificatesNo());
        paramMap.put("sex",patient.getSex());
        paramMap.put("birthdate", patient.getBirthdate());
        paramMap.put("phone",patient.getPhone());
        paramMap.put("isMarry", patient.getIsMarry());
        paramMap.put("provinceCode",patient.getProvinceCode());
        paramMap.put("cityCode", patient.getCityCode());
        paramMap.put("districtCode",patient.getDistrictCode());
        paramMap.put("address",patient.getAddress());
        //联系人
        paramMap.put("contactsName",patient.getContactsName());
        paramMap.put("contactsCertificatesType", patient.getContactsCertificatesType());
        paramMap.put("contactsCertificatesNo",patient.getContactsCertificatesNo());
        paramMap.put("contactsPhone",patient.getContactsPhone());
        paramMap.put("timestamp", HttpRequestHelper.getTimestamp());
        //String sign = HttpRequestHelper.getSign(paramMap, signInfoVo.getSignKey());
        paramMap.put("sign", "");//秘钥
        //使用httpclient发送请求，请求医院接口
        JSONObject jsonObject = HttpRequestHelper.sendRequest(paramMap, "http://localhost:8201/order/submitOrder");
        int code = jsonObject.getInteger("code");
        if (code == 200){//预约挂号成功，保存订单新
            //可以挂号

            //第一步：要把上面的到的三部分数据插入到order_info表
            OrderInfo orderInfo = new OrderInfo();
            //如果返回成功，得返回其他数据
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            //预约记录唯一标识（医院预约纪录主键）
            String hosRecordId = jsonObject1.getString("hosRecordId");
            //预约序号
            String number = jsonObject1.getString("number");
            //取号时间
            String fetchTime = jsonObject1.getString("fetchTime");
            //取号地址
            String fetchAddress = jsonObject1.getString("fetchAddress");
            //设置添加数据--排班数据
            BeanUtils.copyProperties(scheduleOrderVo, orderInfo);
            //设置添加数据--就诊人数据
            //订单号
            String outTradeNo = System.currentTimeMillis() + ""+ new Random().nextInt(100);
            orderInfo.setOutTradeNo(outTradeNo);//订单交易号
            orderInfo.setScheduleId(scheduleOrderVo.getHosScheduleId());//排班id
            orderInfo.setUserId(patient.getUserId());//用户id
            orderInfo.setPatientId(patientId);//就诊人id
            orderInfo.setPatientName(patient.getName());//就诊人名字
            orderInfo.setPatientPhone(patient.getPhone());//就诊人电话
            orderInfo.setOrderStatus(OrderStatusEnum.UNPAID.getStatus());//设置订单状态，预约成功待支付
            //设置添加数据--医院接口返回数据
            orderInfo.setHosRecordId(hosRecordId);
            orderInfo.setNumber(Integer.valueOf(number));
            orderInfo.setFetchTime(fetchTime);
            orderInfo.setFetchAddress(fetchAddress);
            baseMapper.insert(orderInfo);

            //第二步：更新排班数据中的剩余人数,使用rabbitmq解耦合
            Integer availableNumber = jsonObject1.getInteger("availableNumber");
            Integer reserveNumber = jsonObject1.getInteger("reserveNumber");
            OrderMqVo orderMqVo = new OrderMqVo();
            orderMqVo.setReservedNumber(reserveNumber);
            orderMqVo.setAvailableNumber(availableNumber);
            orderMqVo.setScheduleId(scheduleId);
            MsmVo msmVo = new MsmVo();
            msmVo.setPhone(patient.getPhone());
            //短信提示
            msmVo.setPhone(orderInfo.getPatientPhone());
            String reserveDate =
                    new DateTime(orderInfo.getReserveDate()).toString("yyyy-MM-dd")
                            + (orderInfo.getReserveTime()==0 ? "上午": "下午");
            Map<String,Object> param = new HashMap<String,Object>(){{
                put("title", orderInfo.getHosname()+"|"+orderInfo.getDepname()+"|"+orderInfo.getTitle());
                put("amount", orderInfo.getAmount());
                put("reserveDate", reserveDate);
                put("name", orderInfo.getPatientName());
                put("quitTime", new DateTime(orderInfo.getQuitTime()).toString("yyyy-MM-dd HH:mm"));
            }};
            msmVo.setParam(param);
            msmVo.setTemplateCode("您{code}预约的号议程");
            orderMqVo.setMsmVo(msmVo);
            rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_ORDER,MqConst.ROUTING_ORDER,orderMqVo);

            //第三步：给就诊人发送预约成功，短信提醒
            //第四步：返回订单id
            return orderInfo.getId();

        }else {//不能挂号，抛出异常
            throw new YyghException(20001,"挂号异常");
        }
    }

    @Override
    public IPage<OrderInfo> selectPage(Page<OrderInfo> pageParam, OrderQueryVo orderQueryVo) {
        //orderQueryVo获取条件值
        String name = orderQueryVo.getKeyword(); //医院名称
        Long patientId = orderQueryVo.getPatientId(); //就诊人名称
        String orderStatus = orderQueryVo.getOrderStatus(); //订单状态
        String reserveDate = orderQueryVo.getReserveDate();//安排时间
        String createTimeBegin = orderQueryVo.getCreateTimeBegin();
        String createTimeEnd = orderQueryVo.getCreateTimeEnd();
        //对条件值进行非空判断
        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(name)) {
            wrapper.like("hosname",name);
        }
        if(!StringUtils.isEmpty(patientId)) {
            wrapper.eq("patient_id",patientId);
        }
        if(!StringUtils.isEmpty(orderStatus)) {
            wrapper.eq("order_status",orderStatus);
        }
        if(!StringUtils.isEmpty(reserveDate)) {
            wrapper.ge("reserve_date",reserveDate);
        }
        if(!StringUtils.isEmpty(createTimeBegin)) {
            wrapper.ge("create_time",createTimeBegin);
        }
        if(!StringUtils.isEmpty(createTimeEnd)) {
            wrapper.le("create_time",createTimeEnd);
        }
        //调用mapper的方法
        IPage<OrderInfo> pages = baseMapper.selectPage(pageParam, wrapper);
        //编号变成对应值封装
        pages.getRecords().stream().forEach(item -> {
            this.packOrderInfo(item);
        });
        return pages;
    }

    @Override
    public OrderInfo getOrderInfo(Long orderId) {
        OrderInfo orderInfo = baseMapper.selectById(orderId);
        return this.packOrderInfo(orderInfo);
    }

    @Override
    public Boolean cancelOrder(Long orderId) {
        //1.先判断当前时间是否已经过了平台规定的退号截止时间
        OrderInfo orderInfo = baseMapper.selectById(orderId);
        DateTime quitTime = new DateTime(orderInfo.getQuitTime());//将截止时间转化为DataTime的格式
        if (quitTime.isBeforeNow()) {//截止时间在当前时间之前执行
            throw new YyghException(2001, "已经超过了退号的截止时间");
        }
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("hoscode", orderInfo.getHoscode());
        reqMap.put("hosRecordId", orderInfo.getHosRecordId());
        reqMap.put("timestamp", HttpRequestHelper.getTimestamp());
        reqMap.put("sign", "");
        JSONObject jsonObject = HttpRequestHelper.sendRequest(reqMap, "http://localhost:8201/order/updateCancelStatus");
        if (jsonObject.getInteger("code") != 200) {
            throw new YyghException(ResultCodeEnum.FAIL.getCode(), jsonObject.getString("message"));
        } else {
            //是否支付 退款
            if (orderInfo.getOrderStatus().intValue() == OrderStatusEnum.PAID.getStatus().intValue()) {
                //已支付 退款
                Boolean isRefund = weixinService.refund(orderId);
                if (!isRefund) {
                    throw new YyghException(20001, "退款失败");
                }
            }
            //3.更新订单表订单状态（order_status）、支付记录表的支付状态（payment_status）
            //更改订单状态
            orderInfo.setOrderStatus(OrderStatusEnum.CANCLE.getStatus());
            this.updateById(orderInfo);
            //更新支付记录状态。
            QueryWrapper<PaymentInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("order_id", orderId);

            PaymentInfo paymentInfo = paymentService.getOne(queryWrapper);
            paymentInfo.setPaymentStatus(-1); //退款
            paymentInfo.setUpdateTime(new Date());
            paymentService.updateById(paymentInfo);
            //4.更新排班数据+1，发送短信提示
            //发送mq信息更新预约数 我们与下单成功更新预约数使用相同的mq信息，不设置可预约数与剩余预约数，接收端可预约数减1即可
            OrderMqVo orderMqVo = new OrderMqVo();
            orderMqVo.setScheduleId(orderInfo.getScheduleId());
            //短信提示,利用rabbitmq管理短信发送
            MsmVo msmVo = new MsmVo();//新建一个消息实体
            msmVo.setPhone(orderInfo.getPatientPhone());
            orderMqVo.setMsmVo(msmVo);
            rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_ORDER, MqConst.ROUTING_ORDER, orderMqVo);
            return true;
        }
    }

    @Override
    public void patientTips() {
        String string = new DateTime().toString("yyyy-MM-dd");
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("reserve_date", string);
        queryWrapper.ne("order_status", OrderStatusEnum.CANCLE.getStatus());
        List<OrderInfo> list = baseMapper.selectList(queryWrapper);
        for (OrderInfo orderInfo : list) {
            //短信提示
            MsmVo msmVo = new MsmVo();
            msmVo.setPhone(orderInfo.getPatientPhone());
            rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_MSM, MqConst.ROUTING_MSM_ITEM, msmVo);
        }
    }

    @Override
    public Map<String, Object> getCountMap(OrderCountQueryVo orderCountQueryVo) {
        Map<String, Object> map = new HashMap<>();
        List<OrderCountVo> orderCountVos = baseMapper.selectOrderCount(orderCountQueryVo);
        //日期列表
        List<String> dateList = orderCountVos.stream().map(OrderCountVo::getReserveDate).collect(Collectors.toList());
        //统计列表
        List<Integer> countList = orderCountVos.stream().map(OrderCountVo::getCount).collect(Collectors.toList());
        map.put("dateList", dateList);
        map.put("countList", countList);
        return map;
    }








    private OrderInfo packOrderInfo (OrderInfo orderInfo){
        orderInfo.getParam().put("orderStatusString", OrderStatusEnum.getStatusNameByStatus(orderInfo.getOrderStatus()));
        return orderInfo;
    }
}
