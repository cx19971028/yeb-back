package com.njtech.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.njtech.server.pojo.MenuRole;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenxin
 * @since 2021-09-12
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    int updateRoleMenu(@Param("rid") Integer rid, @Param("mids") Integer[] mids);
}
