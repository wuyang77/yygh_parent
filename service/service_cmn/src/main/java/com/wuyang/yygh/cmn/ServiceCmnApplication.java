package com.wuyang.yygh.cmn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = MongoAutoConfiguration.class)
@ComponentScan(basePackages = {"com.wuyang"})//用于swagger,不加只会扫描当前模块的配置类信息
@MapperScan(basePackages = "com.wuyang.yygh.cmn.mapper") //作用：指定要变成实现类的接口所在的包，包下面的所有接口在编译之后都会生成相应的实现类
@EnableDiscoveryClient //开启Nacos客户端支持
public class ServiceCmnApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceCmnApplication.class,args);
    }
}
