package com.wuyang.yygh.orders.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.wuyang.yygh.common.exception.YyghException;
import com.wuyang.yygh.enums.RefundStatusEnum;
import com.wuyang.yygh.model.order.OrderInfo;
import com.wuyang.yygh.model.order.PaymentInfo;
import com.wuyang.yygh.model.order.RefundInfo;
import com.wuyang.yygh.orders.service.OrderInfoService;
import com.wuyang.yygh.orders.service.PaymentService;
import com.wuyang.yygh.orders.service.RefundInfoService;
import com.wuyang.yygh.orders.service.WeixinService;
import com.wuyang.yygh.orders.utils.ConstantPropertiesUtils;
import com.wuyang.yygh.orders.utils.HttpClient;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class WeiXinServiceImpl implements WeixinService {

    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private RefundInfoService refundInfoService;
    //根据订单id要获取生成微信支付二维码所需要的数据
    @Override
    public Map createNative(Long orderId){
        try {
            //1.根据订单id获取订单信息
            OrderInfo orderinfo = orderInfoService.getOrderInfo(orderId);
            //2.保存支付交易记录
            paymentService.savePaymentInfo(orderinfo,2);
            //3.先准备参数，xml格式，调用微信服务接口进行支付
            //1、设置参数
            Map paramMap = new HashMap();
            paramMap.put("appid", ConstantPropertiesUtils.APPID);//公众账号ID
            paramMap.put("mch_id", ConstantPropertiesUtils.PARTNER);//商户号
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
            Date reserveDate = orderinfo.getReserveDate();//获取订单的安排日期
            String reserveDateString = new DateTime(reserveDate).toString("yyyy/MM/dd");
            String body = reserveDateString + "就诊"+ orderinfo.getDepname();//安排日期+“就诊”+科室名称
            paramMap.put("body", body);
            paramMap.put("out_trade_no", orderinfo.getOutTradeNo());//商户订单号
            //paramMap.put("total_fee", order.getAmount().multiply(new BigDecimal("100")).longValue()+"");
            paramMap.put("total_fee", "1");//标价金额(为了测试,不要冒险，支付之后可能退不回来，金额写小点给它写死了，单位为1分)
            paramMap.put("spbill_create_ip", "127.0.0.1");//终端ip：使用pos机,这里用不上
            paramMap.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify");//回调地址（通知地址）
            paramMap.put("trade_type", "NATIVE");//交易类型
            //2、HTTPClient来根据URL访问第三方接口并且传递参数
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            httpClient.setXmlParam(WXPayUtil.generateSignedXml(paramMap,ConstantPropertiesUtils.PARTNERKEY));
            httpClient.setHttps(true);
            httpClient.post();
            //3、返回第三方的数据
            String content = httpClient.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);
            //4、封装返回结果集
            Map map = new HashMap<>();
            map.put("orderId", orderId);
            map.put("totalFee", orderinfo.getAmount());//返回总的费用
            map.put("resultCode", resultMap.get("result_code"));//返回状态码
            map.put("codeUrl", resultMap.get("code_url"));//二维码链接
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap();
        }
    }

    @Override
    public Map<String, String> queryPayStatus(Long orderId, String paymentType) {
        try {
            OrderInfo orderInfo = orderInfoService.getOrderInfo(orderId);
            //1.封装参数(必填的参数)
            Map paramMap = new HashMap<>();
            paramMap.put("appid", ConstantPropertiesUtils.APPID);//公众账号ID
            paramMap.put("mch_id", ConstantPropertiesUtils.PARTNER);//商户号
            paramMap.put("out_trade_no", orderInfo.getOutTradeNo());//商户订单号
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
            //2.设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(paramMap,ConstantPropertiesUtils.PARTNERKEY));//生成带有sign的XML格式字符串
            client.setHttps(true);
            client.post();
            //3.返回第三方的数据，转成Map
            String contentXml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(contentXml);
            //4.返回结果
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean refund(Long orderId) {
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("order_id",orderId);
            PaymentInfo paymentInfo = paymentService.getOne(queryWrapper);
            if (paymentInfo == null){
                throw new YyghException(20001,"没有该订单的支付记录");
            }
            RefundInfo refundInfo = refundInfoService.saveRefundInfo(paymentInfo);
            Map<String,String> paramMap = new HashMap<>(8);
            paramMap.put("appid",ConstantPropertiesUtils.APPID);//公众账号ID
            paramMap.put("mch_id",ConstantPropertiesUtils.PARTNER);//商户编号
            paramMap.put("nonce_str",WXPayUtil.generateNonceStr());//生产随机字符串
            paramMap.put("transaction_id",paymentInfo.getTradeNo()); //微信订单号
            paramMap.put("out_trade_no",paymentInfo.getOutTradeNo()); //商户订单编号
            paramMap.put("out_refund_no","tk"+paymentInfo.getOutTradeNo()); //商户退款单号
            //       paramMap.put("total_fee",paymentInfo.getTotalAmount().multiply(new BigDecimal("100")).longValue()+"");
            //       paramMap.put("refund_fee",paymentInfo.getTotalAmount().multiply(new BigDecimal("100")).longValue()+"");
            paramMap.put("total_fee","1");
            paramMap.put("refund_fee","1");

            String paramXml = WXPayUtil.generateSignedXml(paramMap, ConstantPropertiesUtils.PARTNERKEY);
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/secapi/pay/refund");
            client.setXmlParam(paramXml);
            client.setHttps(true);
            client.setCert(true);//需要证书支持
            client.setCertPassword(ConstantPropertiesUtils.PARTNER);
            client.post();
            //3、返回第三方的数据
            String content = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);
            if (null != resultMap && WXPayConstants.SUCCESS.equalsIgnoreCase(resultMap.get("result_code"))) {
                refundInfo.setCallbackTime(new Date());
                //退款单号
                refundInfo.setTradeNo(resultMap.get("refund_id"));
                refundInfo.setRefundStatus(RefundStatusEnum.REFUND.getStatus());
                refundInfo.setCallbackContent(JSONObject.toJSONString(resultMap));
                refundInfoService.updateById(refundInfo);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

