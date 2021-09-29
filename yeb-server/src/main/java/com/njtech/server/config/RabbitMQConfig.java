package com.njtech.server.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.njtech.server.mapper.MailLogMapper;
import com.njtech.server.pojo.MailLog;
import com.njtech.server.service.IMailLogService;
import com.njtech.server.utils.MailConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 声明队列，交换机，绑定队列和交换机
 * 声明回调机制
 * @author chenxin
 * @date 2021/9/27 10:24
 */
@Slf4j
@Configuration
public class RabbitMQConfig {

    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;

    @Autowired
    private IMailLogService mailLogService;

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        /**
         * 消息确认回调，确认消息是否到达交换机
         *  correlationData:消息唯一标识
         *  ack:确认结果
         *  cause: 失败原因
         */
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause)->{
            String msgId = correlationData.getId();
            if(ack){
                log.info("【消息回调】消息发送成功：{}",msgId);
                LambdaUpdateWrapper<MailLog> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.set(MailLog::getStatus,1).eq(MailLog::getMsgId,msgId);
                mailLogService.update(updateWrapper);
            }else {
                log.info("【消息回调】消息发送失败：{}",msgId);
            }
        });

        /**
         * 消息失败回调， 消息未从交换机到达队列，比如；队列挂掉
         * message:消息主体
         * repCode:响应码
         * repText：响应描述
         * exchange:交换机
         * routineKey
         */
        rabbitTemplate.setReturnCallback((message,repCode,repText,exchange,routineKey)->{
            log.info("【消息回调】消息{}发送到队列的时候失败",new String(message.getBody()));
        });
        return rabbitTemplate;
    }


    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(MailConstants.EXCHANGE_NAME);
    }

    @Bean
    public Queue queue(){
        return new Queue(MailConstants.QUEUE_NAME);
    }

    /**
     * 绑定交换机与队列
     * @return
     */
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(directExchange()).with(MailConstants.ROUTINE_KEY_NAME);
    }
}
