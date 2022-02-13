package com.stylefeng.guns.rest;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.stylefeng.guns.rest.modular.user.UserApiSample;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = {"com.stylefeng.guns"})
@EnableDubboConfiguration
public class GatewayApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(GatewayApplication.class, args);

//        UserApiSample userApiSample = context.getBean(UserApiSample.class);
//
//        boolean result = userApiSample.login("lqyyy", "123456");
//
//        System.out.println("result = " + result);
    }
}
