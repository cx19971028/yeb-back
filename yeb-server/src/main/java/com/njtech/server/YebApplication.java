package com.njtech.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 *  启动类
 * @author chenxin
 * @date 2021/9/12 14:40
 */

@SpringBootApplication
@MapperScan("com.njtech.server.mapper")
public class YebApplication {
    public static void main(String[] args) {
        SpringApplication.run(YebApplication.class,args);
    }
}
