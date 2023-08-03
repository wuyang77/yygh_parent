package com.wuyang.yygh.user;


import com.wuyang.yygh.user.util.ConstantPropertiesUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.wuyang")
@MapperScan("com.wuyang.yygh.user.mapper")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.wuyang")
public class ServiceUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceUserApplication.class,args);
    }
}
//1.session复制，比较消耗网络
//2.session+redis+cookie方式:需要专门维护redis服务，每次请求都需要去redis进行验证
//3.token+cookie:方式 token:按着-定的规则[别人不知道,自己知道]生成一个字符串[含有用户]
// JWT:生成token的一个工具