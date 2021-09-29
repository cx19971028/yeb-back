package com.njtech.server.service.impl;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njtech.server.pojo.Employee;
import com.njtech.server.mapper.EmployeeMapper;
import com.njtech.server.pojo.MailLog;
import com.njtech.server.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njtech.server.service.IMailLogService;
import com.njtech.server.utils.MailConstants;
import com.njtech.server.vo.PageResult;
import com.njtech.server.vo.Result;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenxin
 * @since 2021-09-12
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private IMailLogService mailLogService;

    /**
     * 获取最大工号
     * @return
     */
    @Override
    public String getMaxWorkId() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("max(workID)");
        List<Map<String, Object>> maps = employeeMapper.selectMaps(queryWrapper);
        Integer workID = Integer.parseInt(maps.get(0).get("max(workID)").toString()) + 1;
        return String.format("%08d",workID);
    }

    /**
     * 添加员工
     * @param employee
     * @return
     */
    @Override
    public Result addEmp(Employee employee) {
        LocalDate beginContract = employee.getBeginContract();
        LocalDate endContract = employee.getEndContract();
        long until = beginContract.until(endContract, ChronoUnit.DAYS);  // 计算两个日期之间的时间间隔 单位：天
        DecimalFormat df = new DecimalFormat("##.00"); // 格式化成两位小数
        Double yearInterval = Double.valueOf(df.format(until/365.00));
        employee.setContractTerm(yearInterval);
        if(1==employeeMapper.insert(employee)){
            Employee emp = employeeMapper.getEmployeeInfo(employee.getId()).get(0);
            // 设置标志位
            MailLog mailLog = new MailLog();
            String msgId = UUID.randomUUID().toString();
            mailLog.setMsgId(msgId);
            mailLog.setEid(employee.getId());
            mailLog.setStatus(0);
            mailLog.setRouteKey(MailConstants.ROUTINE_KEY_NAME);
            mailLog.setExchange(MailConstants.EXCHANGE_NAME);
            mailLog.setCount(0);
            mailLog.setTryTime(LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT));
            mailLog.setCreateTime(LocalDateTime.now());
            mailLog.setUpdateTime(LocalDateTime.now());
            mailLogService.save(mailLog);

            // 通过交换机，发送信息routineKey:mail.welcome
            // CorrelationData() 设置发送消息的ID
            rabbitTemplate.convertAndSend(MailConstants.EXCHANGE_NAME,MailConstants.ROUTINE_KEY_NAME,emp,new CorrelationData(msgId));
            return Result.success("添加成功");
        }
        return Result.success("添加失败");
    }

    @Override
    public List<Employee> getEmployeeInfo(Integer id) {
        List<Employee> employeeList = employeeMapper.getEmployeeInfo(id);
        return employeeList;
    }

    /**
     * 获取所有员工及工资套账
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public Result getEmployeeWithSalaries(Integer page, Integer pageSize) {
        Page<Employee> employeePage = new Page<>(page,pageSize);
        IPage<Employee> employeeIPage = employeeMapper.getEmployeeWithSalaries(employeePage);
        PageResult<Employee> employeePageResult = new PageResult<>(employeeIPage.getTotal(),employeeIPage.getRecords());
        return Result.success(employeePageResult);
    }
}
