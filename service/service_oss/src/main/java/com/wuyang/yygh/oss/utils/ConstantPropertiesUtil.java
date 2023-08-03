package com.wuyang.yygh.oss.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 常量类，读取配置文件application.properties中的配置
 */
@Component
//@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "aliyun.oss.file")//该成员变量的属性前缀
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConstantPropertiesUtil{
    private String endpoint;
    private String keyid;
    private String keysecret;
    private String bucketname;
}