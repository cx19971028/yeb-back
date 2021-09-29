package com.njtech.server.controller;


import com.njtech.server.pojo.Salary;
import com.njtech.server.service.ISalaryService;
import com.njtech.server.vo.Result;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenxin
 * @since 2021-09-12
 */
@RestController
@RequestMapping("/salary/sob")
public class SalaryController {

    @Autowired
    private ISalaryService salaryService;

    @ApiModelProperty("获取所有的工资账套")
    @GetMapping("/")
    public Result getAllSalary(){
        List<Salary> salaryList = salaryService.list();
        return Result.success(salaryList);
    }

    @ApiModelProperty("添加工资账套")
    @PostMapping("/")
    public Result addSalary(@RequestBody Salary salary){
        if(salaryService.save(salary)){
            return Result.success("添加成功！");
        }
        return Result.fail("添加失败！");
    }

    @ApiModelProperty("更新工资账套")
    @PutMapping("/")
    public Result updateSalary(@RequestBody Salary salary){
        if(salaryService.updateById(salary)){
            return Result.success("更新成功！");
        }
        return Result.fail("更新失败！");
    }

    @ApiModelProperty("删除工资账套")
    @DeleteMapping("/{id}")
    public Result deleteSalary(@PathVariable("id") Integer id){
        if(salaryService.removeById(id)){
            return Result.success("删除成功！");
        }
        return Result.fail("删除失败！");
    }

}
