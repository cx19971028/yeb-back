package com.njtech.mail;

import com.njtech.server.utils.MailConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;


/**
 *
 *  启动类
 * @author chenxin
 * @date 2021/9/12 14:40
 */

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class YebMailApplication {
    public static void main(String[] args) {
        SpringApplication.run(YebMailApplication.class,args);
    }

    @Bean
    public Queue queue(){
        return new Queue(MailConstants.QUEUE_NAME);
    }
}
