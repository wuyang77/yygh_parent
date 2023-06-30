package com.wuyang.yygh.msm.service.impl;

import com.wuyang.yygh.msm.service.MsmService;
import com.wuyang.yygh.msm.utils.HttpUtils;
import com.wuyang.yygh.msm.utils.RandomUtil;
import com.wuyang.yygh.vo.msm.MsmVo;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class MsmServiceImpl implements MsmService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public boolean sendCode(String phone) {

        String redisPhone = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(redisPhone)) {
            return true;
        }//防止用户绕过前端不断地给你发请求，判断Redis中有没有这个短信验证码
        String host = "https://gyytz.market.alicloudapi.com";
        String path = "/sms/smsSend";
        String method = "POST";
        String appcode = "eafa64e56d5a4e9d9cc091f9c54302d1";
        Map<String, String> headers = new HashMap<>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<>();
        querys.put("mobile",phone);

        String code = RandomUtil.getFourBitRandom();
        querys.put("param", "**code**:"+code+",**minute**:5");
        querys.put("smsSignId", "2e65b1bb3d054466b82f0c9d125465e2");
        querys.put("templateId", "908e94ccf08b4476ba6c876d13f084ad");
        Map<String, String> bodys = new HashMap<>();
        try {
            HttpResponse response = HttpUtils.doPost(host,path,method,headers,querys,bodys);
            System.out.println(response.toString());
            redisTemplate.opsForValue().set(phone,code,2, TimeUnit.MINUTES);
            return true;
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void sendNotice(MsmVo msmVo) {

        String host = "https://cxwg.market.alicloudapi.com";
        String path = "/sendSms";
        String method = "POST";
        String appcode = "eafa64e56d5a4e9d9cc091f9c54302d1";//开通服务后 买家中心-查看AppCode
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("content", "【北京市医院】模拟：就诊人预约挂号成功短信提示...请及时就医");
        querys.put("mobile", "17375588560");
        Map<String, String> bodys = new HashMap<String, String>();

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("模拟：就诊人预约挂号成功短信提示...");

    }

}
