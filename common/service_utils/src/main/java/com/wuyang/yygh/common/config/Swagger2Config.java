package com.wuyang.yygh.common.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2//开启Swagger
public class Swagger2Config {
//    @Bean
//    public Docket getDocket(){
//        return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo()).groupName("管理员系统的接口").select().paths(Predicates.and(PathSelectors.regex("/admin/.*"))).build();
//    }
//    public ApiInfo getApiInfo(){
//        return new ApiInfo("接口文档|测试文档", "我的第一个Swagger接口文档生成测试",
//                "1.0",
//                "http://www.atguigu.com",
//                new Contact("吴","Hunan Normal University","2388958622@qq.com"),
//                "这是证书，点击进入官网",
//                "http://www.atguigu.com",
//                new ArrayList<VendorExtension>());
//    }

    @Bean
    public Docket webApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                //只显示api路径下的页面
                //.paths(Predicates.and(PathSelectors.regex("/api/.*")))
                .build();
    }

    @Bean
    public Docket adminApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("adminApi")
                .apiInfo(adminApiInfo())
                .select()
                //只显示admin路径下的页面
                .paths(Predicates.and(PathSelectors.regex("/admin/.*")))
                .build();
    }

    private ApiInfo webApiInfo(){
        return new ApiInfoBuilder()
                .title("网站-API文档")
                .description("本文档描述了网站微服务接口定义")
                .version("1.0")
                .contact(new Contact("wuyang", "https://github.com/wuyang77?tab=repositories", "2388958622@qq.com"))
                .build();
    }

    private ApiInfo adminApiInfo(){
        return new ApiInfoBuilder()
                .title("后台管理系统-API文档")
                .description("本文档描述了后台管理系统微服务接口定义")
                .version("1.0")
                .contact(new Contact("wuyang", "https://github.com/wuyang77?tab=repositories", "2388958622@qq.com"))
                .build();
    }

}