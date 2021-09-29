package com.njtech.mail;

import com.njtech.server.pojo.Employee;
import com.njtech.server.utils.MailConstants;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;

/**
 * 消息接收者
 * @author chenxin
 * @date 2021/9/25 13:35
 */
@Slf4j
@Component
public class MailReceiver {

//    @Autowired
//    private JavaMailSender javaMailSender;
//    @Autowired
//    private MailProperties mailProperties;
//    @Autowired
//    private TemplateEngine templateEngine;

    @Autowired
    private RedisTemplate redisTemplate;

    @RabbitListener(queues = MailConstants.QUEUE_NAME)
    public void handler(Message message, Channel channel){
        // 获得员工类
        Employee employee = (Employee) message.getPayload();
        // 获得消息的序号,用于手动应答
        MessageHeaders headers = message.getHeaders();
        long tag = (long)headers.get(AmqpHeaders.DELIVERY_TAG);

        // 获得messageId
        String messageId = (String)headers.get("spring_returned_message_correlation");

       HashOperations hashOperations = redisTemplate.opsForHash();

       try {
           if(hashOperations.entries("mail_log").containsKey(messageId)){
               log.error("消息已经被消费================={}",messageId);
               /**
                * 手动确认消息
                *  tag:消息序号
                *  multiple:是否确认多条
                */
               channel.basicAck(tag,false);
               return;
           }
            log.info("电子邮件服务模块收到消息：{}",employee);
            // 将消息ID存入redis
            hashOperations.put("mail_log",messageId,"ok");
            // 手动确认消息
            channel.basicAck(tag,false);
        } catch (IOException e) {
           try {
               /**
                * 手动确认消息
                *   tage: 消息序号
                *   multiple：是否确认多条
                *   return: 是否确认多条
                */
               channel.basicNack(tag,false,true);
           } catch (IOException ioException) {
               ioException.printStackTrace();
           }
           e.printStackTrace();
        }
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
//
//        try {
//            // 发件人
//            mimeMessageHelper.setFrom(mailProperties.getUsername());
//            // 收件人
//            mimeMessageHelper.setTo(employee.getEmail());
//            // 主题
//            mimeMessageHelper.setSubject("欢迎入职");
//            // 发送日期
//            mimeMessageHelper.setSentDate(new Date());
//            // 邮件内容
//            Context context = new Context();
//            context.setVariable("name",employee.getName());
//            context.setVariable("posName",employee.getPosition().getName());
//            context.setVariable("joblevelName",employee.getJoblevel().getName());
//            context.setVariable("departmentName",employee.getDepartment().getName());
//            String mail = templateEngine.process("mail", context);
//            mimeMessageHelper.setText(mail,true);
//            // 发送邮件
//            javaMailSender.send(mimeMessage);
//        } catch (MessagingException e) {
//            log.info("邮件发送失败---------{}", e.getMessage());
//        }
    }

}
