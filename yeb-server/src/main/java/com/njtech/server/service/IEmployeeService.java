package com.njtech.server.service;

import com.njtech.server.pojo.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njtech.server.vo.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenxin
 * @since 2021-09-12
 */
public interface IEmployeeService extends IService<Employee> {

    /**
     * 获取最大工号
     * @return
     */
    String getMaxWorkId();

    /**
     * 添加员工
     * @param employee
     * @return
     */
    Result addEmp(Employee employee);

    /**
     * 根据id查询员工数据
     * @param id
     * @return
     */
    List<Employee> getEmployeeInfo(Integer id);

    /**
     * 获取所有员工及其工资套账
     * @param page
     * @param pageSize
     * @return
     */
    Result getEmployeeWithSalaries(Integer page, Integer pageSize);
}
