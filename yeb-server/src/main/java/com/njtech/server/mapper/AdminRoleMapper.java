package com.njtech.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.njtech.server.pojo.AdminRole;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenxin
 * @since 2021-09-14
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    /**
     * 更新操操作员角色
     * @param adminId
     * @param ids
     * @return
     */
    Integer addAdminRole(@Param("adminId") Integer adminId, @Param("rids") Integer[] rids);
}
