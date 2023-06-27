package com.wuyang.yygh.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.wuyang.yygh.common.result.R;
import com.wuyang.yygh.common.utils.JwtHelper;
import com.wuyang.yygh.model.user.UserInfo;
import com.wuyang.yygh.user.service.UserInfoService;
import com.wuyang.yygh.user.util.ConstantPropertiesUtil;
import com.wuyang.yygh.user.util.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/ucenter/wx")
public class WeixinController {


    @Autowired
    private UserInfoService userInfoService;
    @RequestMapping("/callback")
    public String callback(String code,String state){
        System.out.println(code+"==="+state);//后端测试收否返回了code和state

        //String url = https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code;
        //HttpClientUtils.get("url");传统的方式写死了，注释掉，推荐下面的方法
        //通过code获取access_token(通过code加上appid和appsecret换取access_token)

        StringBuffer baseAccessTokenUrl = new StringBuffer()
                .append("https://api.weixin.qq.com/sns/oauth2/access_token")
                .append("?appid=%s")
                .append("&secret=%s")
                .append("&code=%s")
                .append("&grant_type=authorization_code");
        String accessTokenUrl = String.format(baseAccessTokenUrl.toString(),
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code);

        try {
            String jsonStr = HttpClientUtils.get(accessTokenUrl);
            System.out.println(jsonStr);//后端查看HttpClient返回的json字符串

            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String accessToken = jsonObject.getString("access_token");
            String openid = jsonObject.getString("openid");
            System.out.println("accessToken = " + accessToken);
            System.out.println("openid = " + openid);//后端查看accessToken和openid

//            第二种通过Gson得到的结果是一样的
//            Gson gson = new Gson();
//            Map map = gson.fromJson(jsonStr, Map.class);
//            String accessToken1 = (String) map.get("access_token");
//            String openid1 = (String) map.get("openid");
//            System.out.println("accessToken1 = " + accessToken1);
//            System.out.println("openid1 = " + openid1);

            UserInfo userInfo = userInfoService.selectByOpenId(openid);
            if (userInfo == null) {//说明微信是首次登录，需要注册
                 userInfo = new UserInfo();
                 //根据access_token和openid访问微信服务器去调取用户的信息
                //HttpClientUtils.get("https://api.weixin.qq.com/sns/userinfo?access_token=&openid=OPENID");
                String userInfoStr = HttpClientUtils.get("https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid="+openid);
                System.out.println("扫描微信后从微信服务器获取到的用户的信息" + userInfoStr);//后端查看从服务器端获取到的用户信息
                JSONObject jsonObject1 = JSONObject.parseObject(userInfoStr);
                userInfo.setOpenid(openid);
                userInfo.setStatus(1);
                userInfo.setNickName(jsonObject1.getString("nickname"));
                userInfoService.save(userInfo);
            }
            //返回用户信信息：name，token
            String name = userInfo.getName();
            Map<String,String> map = new HashMap<>();
            if(StringUtils.isEmpty(name)) {
                name = userInfo.getNickName();
            }
            if(StringUtils.isEmpty(name)) {
                name = userInfo.getPhone();
            }
            map.put("name", name);
            //使用jwt生成token字符串
            String token = JwtHelper.createToken(userInfo.getId(),name);
            map.put("token", token); //TODO

            //和手机登录不同，要多返回一个微信openid
            //判断根据openId查询出的userInfo中的手机号是否为空，为空，说明微信和手机号还没有绑定过，返回openid
            //前端拿到不为空openid，说明需要手机号的绑定
            if (StringUtils.isEmpty(userInfo.getPhone())) {
                map.put("openid",openid);
            }else {
                map.put("openid","");//如果openid为空，微信号和手机号绑定了
            }
            return "redirect:http://localhost:3000/weixin/callback?token="+map.get("token")+ "&openid="+map.get("openid")+"&name="+URLEncoder.encode(name,"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取微信登录参数
     */
    @GetMapping("/getLoginParam")
    @ResponseBody
    public R genQrConnect() throws UnsupportedEncodingException {
        String redirectUri = URLEncoder.encode(ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL, "UTF-8");
        Map<String, Object> map = new HashMap<>();
        map.put("appid", ConstantPropertiesUtil.WX_OPEN_APP_ID);
        map.put("redirectUri", redirectUri);
        map.put("scope", "snsapi_login");
        map.put("state", System.currentTimeMillis()+"");//System.currentTimeMillis()+""
        return R.ok().data(map);
    }
}
