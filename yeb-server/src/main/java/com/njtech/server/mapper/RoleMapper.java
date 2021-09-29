package com.njtech.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.njtech.server.pojo.Role;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenxin
 * @since 2021-09-12
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id查询用户角色
     * @param adminId
     * @return
     */
    List<Role> getRolesByAdminId(Integer adminId);
}
