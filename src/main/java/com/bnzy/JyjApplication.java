package com.bnzy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableCaching //开启Spring Cache注解方式是缓存功能
@EnableTransactionManagement//开启事务管理
@EnableScheduling //开启定时任务（定时更新缓存）
public class  JyjApplication {

    public static void main(String[] args) {
        SpringApplication.run(JyjApplication.class, args);
    }

}
