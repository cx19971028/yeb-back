package com.njtech.server.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.njtech.server.pojo.Employee;
import com.njtech.server.pojo.Salary;
import com.njtech.server.service.IEmployeeService;
import com.njtech.server.service.ISalaryService;
import com.njtech.server.vo.Result;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author chenxin
 * @date 2021/9/28 15:59
 */
@RestController
@RequestMapping("/salary/sobcfg")
public class SalarySobCfgController {

    @Autowired
    private ISalaryService salaryService;
    @Autowired
    private IEmployeeService employeeService;

    @ApiModelProperty("获取所有工资套账")
    @GetMapping("/salaries")
    public Result getAllSalaries(){
        List<Salary> salaryList = salaryService.list();
        return Result.success(salaryList);
    }

    @ApiModelProperty("获取所有员工及工资套账")
    @GetMapping("/")
    public Result getEmployeeWithSalaries(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer pageSize){
        return employeeService.getEmployeeWithSalaries(page,pageSize);
    }

    @ApiModelProperty("更新员工工资账套")
    @PutMapping("/")
    public Result updateEmploySalary(Integer eid, Integer sid){
        if(employeeService.update(new LambdaUpdateWrapper<Employee>().set(Employee::getSalaryId,sid).eq(Employee::getId, eid))){
            return Result.success("更新成功！");
        }
        return Result.fail("更新失败！");
    }
}
