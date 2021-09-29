package com.njtech.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.njtech.server.pojo.Menu;
import com.njtech.server.vo.Result;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenxin
 * @since 2021-09-12
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据用户id查询菜单列表
     * @param id
     * @return
     */
    List<Menu> getMenusByAdminId(Integer id);

    /**
     * 根据角色查询所有权限资源
     */
    List<Menu> getAllMenusByRole();

    /**
     * 查询所有菜单及其子菜单
     * @return
     */
    List<Menu> getAllMenus();
}
