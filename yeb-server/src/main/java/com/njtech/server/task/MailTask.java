package com.njtech.server.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.njtech.server.pojo.Employee;
import com.njtech.server.pojo.MailLog;
import com.njtech.server.service.IEmployeeService;
import com.njtech.server.service.IMailLogService;
import com.njtech.server.utils.MailConstants;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 邮件发送定时任务
 * @author chenxin
 * @date 2021/9/27 11:17
 */
@Component
@EnableScheduling
public class MailTask {

    @Autowired
    private IMailLogService mailLogService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 邮件发送定时任务
     * 10s执行一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void mailTask(){
        // 所有未发送成功，且发送达到下次发送时长的消息
        List<MailLog> mailLogList = mailLogService.list(new LambdaQueryWrapper<MailLog>().eq(MailLog::getStatus, 0).lt(MailLog::getTryTime, LocalDateTime.now()));

        mailLogList.forEach(mailLog -> {
            // 如果发送三次失败，则将发送标志位改为发送失败
            if(mailLog.getCount()>=3){
                mailLogService.update(new LambdaUpdateWrapper<MailLog>().set(MailLog::getStatus, MailConstants.FAILURE).eq(MailLog::getMsgId,mailLog.getMsgId()));
            }
            // 更新标志位,增加发送次数
            mailLogService.update(new LambdaUpdateWrapper<MailLog>().set(MailLog::getCount,mailLog.getCount()+1).set(MailLog::getUpdateTime,
                    LocalDateTime.now()).set(MailLog::getTryTime,LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT)).eq(MailLog::getMsgId,mailLog.getMsgId()));
            // 从数据库查询employee信息，重新发送消息
            Employee employee = employeeService.getOne(new LambdaQueryWrapper<Employee>().eq(Employee::getId, mailLog.getEid()));
            rabbitTemplate.convertAndSend(MailConstants.EXCHANGE_NAME,MailConstants.ROUTINE_KEY_NAME, employee,new CorrelationData(mailLog.getMsgId()));
        });
    }
}
