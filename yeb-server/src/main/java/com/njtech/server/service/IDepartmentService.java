package com.njtech.server.service;

import com.njtech.server.pojo.Department;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njtech.server.vo.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenxin
 * @since 2021-09-12
 */
public interface IDepartmentService extends IService<Department> {

    /**
     * 查询所有部门
     * @return
     */
    Result getAllDepartment();

    /**
     * 添加部门
     * @param department
     * @return
     */
    Result addDep(Department department);

    /**
     * 删除部门
     * @param id
     * @return
     */
    Result delDep(Integer id);
}
