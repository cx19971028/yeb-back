package com.njtech.server.controller;


import com.njtech.server.pojo.Department;
import com.njtech.server.service.IDepartmentService;
import com.njtech.server.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenxin
 * @since 2021-09-12
 */
@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    @ApiOperation(value = "获取所有部门")
    @GetMapping("/")
    public Result getAllDepartment(){
        return departmentService.getAllDepartment();
    }

    @ApiOperation(value = "添加部门")
    @PostMapping("/")
    public Result addDep(@RequestBody Department department){
        return departmentService.addDep(department);
    }

    @ApiOperation(value = "删除部门")
    @DeleteMapping("/{id}")
    public Result delDep(@PathVariable("id") Integer id){
        return departmentService.delDep(id);
    }
}
