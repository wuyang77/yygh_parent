package com.wuyang.yygh.user.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
//@PropertySource("classpath:application.properties")
public class ConstantPropertiesUtil implements InitializingBean {

	@Value("${wx.open.app_id}")//在配置文件中直接获取值
	private String appId;

	@Value("${wx.open.app_secret}")
	private String appSecret;

	@Value("${wx.open.redirect_url}")
	private String redirectUrl;

	public static String WX_OPEN_APP_ID;
	public static String WX_OPEN_APP_SECRET;
	public static String WX_OPEN_REDIRECT_URL;

	@Override
	public void afterPropertiesSet() throws Exception {
		WX_OPEN_APP_ID = appId;
		WX_OPEN_APP_SECRET = appSecret;
		WX_OPEN_REDIRECT_URL = redirectUrl;
	}
}