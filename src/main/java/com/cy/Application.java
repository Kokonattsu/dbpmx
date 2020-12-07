package com.cy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCaching  //启动缓存配置
@EnableAsync    //启动异步线程池
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
