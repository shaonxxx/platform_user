package com.woniu.woniuticket.platform_user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;

@SpringBootApplication
@MapperScan(value = "com.woniu.woniuticket.platform_user.mapper")
@EnableCaching
@EnableEurekaClient
@EnableSpringHttpSession
public class PlatformUserApplication  implements TransactionManagementConfigurer {

    @Resource(name = "txManager")
    private PlatformTransactionManager txManager;

    //创建事务管理
    @Bean
    public PlatformTransactionManager txManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    // 实现接口 TransactionManagementConfigurer 方法，其返回值代表在拥有多个事务管理器的情况下默认使用的事务管理器
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return txManager;
    }

    public static void main(String[] args) {
        SpringApplication.run(PlatformUserApplication.class, args);
    }

}
