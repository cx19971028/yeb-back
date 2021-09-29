package com.njtech.server.service;

import com.njtech.server.pojo.Employee;
import com.njtech.server.pojo.EmployeeEc;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njtech.server.vo.Result;

import java.time.LocalDate;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenxin
 * @since 2021-09-12
 */
public interface IEmployeeEcService extends IService<EmployeeEc> {

    /**
     * 获取所有员工
     * @param currentPage
     * @param pageSize
     * @param employee
     * @param beginDateScopes
     * @return
     */
    Result getAllEmployee(Integer currentPage, Integer pageSize, Employee employee, LocalDate[] beginDateScopes);
}
