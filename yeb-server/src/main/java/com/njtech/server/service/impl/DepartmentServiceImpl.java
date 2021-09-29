package com.njtech.server.service.impl;

import com.njtech.server.pojo.Department;
import com.njtech.server.mapper.DepartmentMapper;
import com.njtech.server.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njtech.server.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenxin
 * @since 2021-09-12
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {


    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 查询所有部门
     * @return
     */
    @Override
    public Result getAllDepartment() {
        List<Department> departmentList = departmentMapper.getAllDepartment(-1);
        return Result.success(departmentList);
    }

    @Override
    public Result addDep(Department department) {
        department.setEnabled(true);
        departmentMapper.addDep(department);
        if(department.getResult()==1){
            return Result.success("添加成功",department);
        }
        return Result.fail("添加失败");
    }

    @Override
    public Result delDep(Integer id) {
        Department department = new Department();
        department.setId(id);
        departmentMapper.deleteDep(department);
        if(department.getResult() == -2){
            return Result.fail("该部门下还有子部门，删除失败！");
        }
        if(department.getResult() == -1){
            return Result.fail("该部门下还有员工，删除失败！");
        }
        if(department.getResult() == 1){
            return Result.success("删除成功！");
        }
        return Result.success("删除失败！");
    }
}
