package com.woniu.woniuticket.platform_user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(value = "com.woniu.woniuticket.platform_user.mapper")
@EnableCaching
public class PlatformUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformUserApplication.class, args);
    }

}
