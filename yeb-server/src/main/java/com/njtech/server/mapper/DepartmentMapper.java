package com.njtech.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.njtech.server.pojo.Department;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenxin
 * @since 2021-09-12
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * 查询所有菜单
     * @param i
     * @return
     */
    List<Department> getAllDepartment(int parentId);

    /**
     * 添加部门
     * @param department
     */
    void addDep(Department department);

    /**
     * 删除部门
     * @param department
     */
    void deleteDep(Department department);
}
