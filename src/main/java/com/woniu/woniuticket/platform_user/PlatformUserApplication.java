package com.woniu.woniuticket.platform_user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@MapperScan(value = "com.woniu.woniuticket.platform_user.mapper")
@EnableCaching
/*@EnableEurekaClient*/
public class PlatformUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformUserApplication.class, args);
    }

}
