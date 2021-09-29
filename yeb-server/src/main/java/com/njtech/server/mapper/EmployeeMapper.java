package com.njtech.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njtech.server.pojo.Employee;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenxin
 * @since 2021-09-12
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 分页查询员工信息
     * @param page
     * @param employee
     * @param beginDateScopes
     * @return
     */
    IPage<Employee> getAllEmployee(Page<Employee> page, Employee employee, LocalDate[] beginDateScopes);

    /**
     * 获取员工信息
     * @param id
     * @return
     */
    List<Employee> getEmployeeInfo(Integer id);

    /**
     * 获取所有员工及工资套账
     * @param employeePage
     * @return
     */
    IPage<Employee> getEmployeeWithSalaries(Page<Employee> employeePage);
}
