package com.wuyang.yygh.hosp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = "com.wuyang")
@MapperScan(basePackages = "com.wuyang.yygh.hosp.mapper")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.wuyang")//可以保证当前模块能够扫描当前模块依赖的jar包中的FeignClient接口了,不仅仅扫描当前模块,连当前模块所依赖的jar包中
public class ServiceHospApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceHospApplication.class,args);
    }
}