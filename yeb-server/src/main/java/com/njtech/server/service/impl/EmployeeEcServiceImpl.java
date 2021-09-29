package com.njtech.server.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njtech.server.mapper.EmployeeMapper;
import com.njtech.server.pojo.Employee;
import com.njtech.server.pojo.EmployeeEc;
import com.njtech.server.mapper.EmployeeEcMapper;
import com.njtech.server.service.IEmployeeEcService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njtech.server.vo.PageResult;
import com.njtech.server.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenxin
 * @since 2021-09-12
 */
@Service
public class EmployeeEcServiceImpl extends ServiceImpl<EmployeeEcMapper, EmployeeEc> implements IEmployeeEcService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 分页查询所有员工
     * @param currentPage
     * @param pageSize
     * @param employee
     * @param beginDateScopes
     * @return
     */
    @Override
    public Result getAllEmployee(Integer currentPage, Integer pageSize, Employee employee, LocalDate[] beginDateScopes) {
        Page<Employee> page = new Page<>(currentPage,pageSize);
        IPage<Employee> employeePage = employeeMapper.getAllEmployee(page,employee,beginDateScopes);
        PageResult<Employee> pageResult = new PageResult<>();
        pageResult.setTotalSize(employeePage.getTotal());
        pageResult.setData(page.getRecords());
        return Result.success(pageResult);
    }
}
